package personio.example.demo.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personio.example.demo.dao.OrgChartDao;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.request.OrgChartValidationState;
import personio.example.demo.response.CreateOrgResponse;
import personio.example.demo.model.OrgChart;
import personio.example.demo.validations.ValidationUtils;

import java.util.Optional;

@Service
public class OrgChartService {

    @Autowired
    private OrgChartDao orgChartDao;

    public CreateOrgResponse createOrgChart(@NonNull CreateOrgChartRequest orgChartRequest) {
        OrgChart orgChart = new OrgChart(orgChartRequest);
        OrgChartValidationState validationState = ValidationUtils.validateOrgChartRequest(orgChartRequest, orgChart);
        if (validationState.equals(OrgChartValidationState.VALID)) {
            orgChartDao.persistOrg(orgChart);
            String s = orgChart.getStructuredOrgChart(orgChart.getBoss()).toString();//.replaceAll("\n", "").replaceAll("\\\\", "/");
            System.out.println(s);
            return new CreateOrgResponse(Optional.of(s), "Successfully created org");

        } else {
            if (validationState.equals(OrgChartValidationState.INVALID_LOOP)) return new CreateOrgResponse(Optional.empty(), "Input JSON had a cyclic dependency. Org creation failed!");
            else if (validationState.equals(OrgChartValidationState.INVALID_MULTIPLE_ROOTS)) return new CreateOrgResponse(Optional.empty(), "Input JSON had multiple roots. Org creation failed!");
            else if (validationState.equals(OrgChartValidationState.INVALID_DISCONNECTED_ORG)) return new CreateOrgResponse(Optional.empty(), "Input JSON had disconnected components. Org creation failed!");
            else return new CreateOrgResponse(Optional.empty(), "Unknown error while creating org chart!");
        }
    }

    public OrgChart getOrgChart() {
        return orgChartDao.getOrgChart();
    }

}
