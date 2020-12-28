package personio.example.demo.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personio.example.demo.dao.OrgChartDao;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.request.OrgChartValidationState;
import personio.example.demo.response.CreateOrgResponse;
import personio.example.demo.model.OrgChart;
import personio.example.demo.response.GetManagersResponse;
import personio.example.demo.sql.QueryExecutor;
import personio.example.demo.utils.Utils;
import personio.example.demo.validations.ValidationUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OrgChartService {

    @Autowired
    private OrgChartDao orgChartDao;

    /**
     * This cache is used to speed up GET queries on the org.
     * This is write through cache, where the cache is updated on any update to the data being stored in the DB.
     *
     * When the application is restarted, the cache is lost. In this case, the cache is updated by reading from the DB,
     * whenever the cache is empty.
     */
    private OrgChart orgChartCache;

    public CreateOrgResponse createOrgChart(@NonNull CreateOrgChartRequest orgChartRequest) {
        OrgChart orgChart = new OrgChart(orgChartRequest);
        orgChartCache = orgChart;
        OrgChartValidationState validationState = ValidationUtils.validateOrgChartRequest(orgChartRequest, orgChart);
        if (validationState.equals(OrgChartValidationState.VALID)) {
            if (!orgChartDao.persistOrg(orgChart)) System.out.println("Error in persisting org");
            String s = orgChart.getStructuredOrgChart(orgChart.getBoss()).toString();
            System.out.println(s);
            return new CreateOrgResponse(Optional.of(s), "Successfully created org");

        } else {
            if (validationState.equals(OrgChartValidationState.INVALID_LOOP)) return new CreateOrgResponse(Optional.empty(), "Input JSON had a cyclic dependency. Org creation failed!");
            else if (validationState.equals(OrgChartValidationState.INVALID_DISCONNECTED_ORG)) return new CreateOrgResponse(Optional.empty(), "Input JSON had disconnected components. Org creation failed!");
            else return new CreateOrgResponse(Optional.empty(), "Unknown error while creating org chart!");
        }
    }

    public String getOrgChartFromDB() {
        if (orgChartCache != null) return orgChartCache.getStructuredOrgChart(orgChartCache.getBoss()).toString();

        Map<String, String> employeeRelationships = new HashMap<>();
        try {
            ResultSet resultSet = QueryExecutor.execReads("select * from users");
            while (resultSet.next()) {
                employeeRelationships.put(resultSet.getString("reportee"), resultSet.getString("employee"));
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException | ClassNotFoundException e) {
            Utils.printStackTrace(e);
            return "Error while retrieving records from DB.";
        }
        CreateOrgChartRequest createOrgChartRequest = new CreateOrgChartRequest(employeeRelationships);
        orgChartCache = new OrgChart(createOrgChartRequest);
        return orgChartCache.getStructuredOrgChart(orgChartCache.getBoss()).toString();
    }

    public GetManagersResponse getManagersForEmployee(String name) {
        return orgChartDao.getManagersForEmployee(name);
    }

}
