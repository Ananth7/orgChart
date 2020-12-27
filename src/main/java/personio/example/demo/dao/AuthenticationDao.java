package personio.example.demo.dao;

import personio.example.demo.model.CreateAdmin;

import java.util.Optional;

public interface AuthenticationDao {
    public boolean createAdminUser(CreateAdmin admin);
    public Optional<String> authenticateUser(CreateAdmin admin);
}
