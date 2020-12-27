package personio.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personio.example.demo.dao.AuthenticationDao;
import personio.example.demo.model.Admin;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationDao authenticationDao;

    public boolean createNewAdminUser(Admin admin) {
        return authenticationDao.createAdminUser(admin);
    }

    public Optional<String> authenticateUser(Admin admin) {
        return authenticationDao.authenticateUser(admin);
    }

    public boolean authenticateSession(String sessionId) {
        return authenticationDao.authenticateSession(sessionId);
    }


}
