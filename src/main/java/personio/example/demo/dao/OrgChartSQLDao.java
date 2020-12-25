package personio.example.demo.dao;

import org.springframework.stereotype.Repository;
import personio.example.demo.model.OrgChart;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.response.CreateOrgResponse;
import personio.example.demo.response.GetManagersResponse;

@Repository
public class OrgChartSQLDao implements OrgChartDao {

    @Override
    public CreateOrgResponse createOrg(CreateOrgChartRequest createOrgChartRequest) {
        return null;
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
