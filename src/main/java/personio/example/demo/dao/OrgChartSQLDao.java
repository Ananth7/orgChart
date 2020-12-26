package personio.example.demo.dao;

import org.springframework.stereotype.Repository;
import personio.example.demo.model.OrgChart;
import personio.example.demo.response.GetManagersResponse;

@Repository
public class OrgChartSQLDao implements OrgChartDao {
    @Override
    public OrgChart persistOrg(OrgChart orgChart) {
        // store in sql
        return orgChart;
    }

    @Override
    public OrgChart getOrgChart() {
        return null;
    }

    @Override
    public GetManagersResponse getManagerForEmployee(String name) {
        return null;
    }
}
