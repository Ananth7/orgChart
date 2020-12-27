package personio.example.demo.dao;

import personio.example.demo.model.Admin;
import personio.example.demo.model.Session;

import java.util.Optional;

public interface AuthenticationDao {
    public boolean createAdminUser(Admin admin);
    public Optional<String> authenticateUser(Admin admin);
    public boolean authenticateSession(String sessionId);
}
