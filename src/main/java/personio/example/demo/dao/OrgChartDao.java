package personio.example.demo.dao;

import personio.example.demo.model.OrgChart;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.response.CreateOrgResponse;
import personio.example.demo.response.GetManagersResponse;

public interface OrgChartDao {
    public CreateOrgResponse createOrg(CreateOrgChartRequest createOrgChartRequest);

    public OrgChart getOrgChart();

    public GetManagersResponse getManagerForEmployee(String name);
}
