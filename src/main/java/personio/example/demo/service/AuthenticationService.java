package personio.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personio.example.demo.dao.AuthenticationDao;
import personio.example.demo.model.CreateAdmin;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationDao authenticationDao;

    public boolean createNewAdminUser(CreateAdmin admin) {
        return authenticationDao.createAdminUser(admin);
    }

    public Optional<String> authenticateUser(CreateAdmin admin) {
        return authenticationDao.authenticateUser(admin);
    }

}
