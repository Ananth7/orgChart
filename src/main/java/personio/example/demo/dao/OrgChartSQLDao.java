package personio.example.demo.dao;

import org.springframework.stereotype.Repository;
import personio.example.demo.model.OrgChart;
import personio.example.demo.response.GetManagersResponse;
import personio.example.demo.sql.QueryExecutor;

import java.sql.SQLException;

@Repository
public class OrgChartSQLDao implements OrgChartDao {
    @Override
    public boolean persistOrg(OrgChart orgChart) {
        try {
            System.out.println("drop table if exists users");
            QueryExecutor.execWrites("drop table if exists users");
            System.out.println("CREATE TABLE users ( `id` bigint(19) NOT NULL AUTO_INCREMENT, `employee` varchar(40) NOT NULL, `reportee` varchar(40) NOT NULL,  PRIMARY KEY (`id`) )ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");
            QueryExecutor.execWrites("CREATE TABLE users ( `id` bigint(19) NOT NULL AUTO_INCREMENT, `employee` varchar(40) NOT NULL, `reportee` varchar(40) NOT NULL,  PRIMARY KEY (`id`) )ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");
            for (String employee: orgChart.getOrgGraph().keySet()) {
                for (String reportee: orgChart.getOrgGraph().get(employee)) {
                    String addShowQuery = "insert into users (employee, reportee) values ('" + employee + "', '" + reportee + "');";
                    System.out.println(addShowQuery);
                    QueryExecutor.execWrites(addShowQuery);
                }
            }
        } catch ( SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
