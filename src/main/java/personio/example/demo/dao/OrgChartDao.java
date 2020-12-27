package personio.example.demo.dao;

import personio.example.demo.model.OrgChart;
import personio.example.demo.response.GetManagersResponse;

public interface OrgChartDao {
    public boolean persistOrg(OrgChart orgChart);

    public OrgChart getOrgChart();

    public GetManagersResponse getManagerForEmployee(String name);
}
