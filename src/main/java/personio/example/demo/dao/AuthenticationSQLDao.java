package personio.example.demo.dao;

import org.springframework.stereotype.Repository;
import personio.example.demo.model.Admin;
import personio.example.demo.model.Session;
import personio.example.demo.sql.QueryExecutor;
import personio.example.demo.utils.AuthenticationUtils;
import personio.example.demo.utils.Utils;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AuthenticationSQLDao implements AuthenticationDao {

    @Override
    public boolean createAdminUser(Admin admin) {
        try {
            String addShowQuery = "insert into session (employee, password, sessionid) values ('"
                    + admin.getUsername() + "', '" + AuthenticationUtils.encrypt(admin.getPassword())
                    + "', '" + UUID.randomUUID().toString() + "');";
            System.out.println(addShowQuery);
                QueryExecutor.execWrites(addShowQuery);
        } catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {
            Utils.printStackTrace(e);
            return false;
        }
        return true;
    }

    @Override
    public Optional<String> authenticateUser(Admin admin) {
        if (admin.getUsername().isEmpty()) return Optional.empty();

        try {
            ResultSet resultSet = QueryExecutor.execReads("select * from session where employee = '" + admin.getUsername() + "';");
            String password = null, sessionId = null;
            while (resultSet.next()) {
                password = resultSet.getString("password");
                sessionId = resultSet.getString("sessionid");
            }
            System.out.println("Password: " + password + " sessionId: " + sessionId);
            resultSet.getStatement().getConnection().close();
            if (AuthenticationUtils.encrypt(admin.getPassword()).equals(password) && sessionId != null) {
                return Optional.of(sessionId);
            } else return Optional.empty();
        } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException e) {
            Utils.printStackTrace(e);
            return Optional.empty();
        }
    }

    @Override
    public boolean authenticateSession(String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) return false;
        return true;

//        try {
//            ResultSet resultSet = QueryExecutor.execReads("select * from session where employee = '" + session.getUsername() + "';");
//            String sessionId = null;
//            while (resultSet.next()) {
//                sessionId = resultSet.getString("sessionid");
//            }
//            System.out.println(" sessionId: " + sessionId);
//            resultSet.getStatement().getConnection().close();
//            System.out.println("sessionid from user = " + session.getSessionId() + " from DB = " + sessionId);
//            if (sessionId != null && session.getSessionId().equals(sessionId)) {
//                return true;
//            } else return false;
//        } catch (SQLException | ClassNotFoundException e) {
//            Utils.printStackTrace(e);
//            return false;
//        }
    }

}
