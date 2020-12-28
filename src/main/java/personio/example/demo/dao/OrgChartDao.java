package personio.example.demo.dao;

import personio.example.demo.model.OrgChart;
import personio.example.demo.response.GetManagersResponse;

/**
 * Interface to interact with the Datastore for storing orgchart.
 * Can be expanded to other datasources in the future.
 */
public interface OrgChartDao {
    public boolean persistOrg(OrgChart orgChart);

    public GetManagersResponse getManagersForEmployee(String name);
}
