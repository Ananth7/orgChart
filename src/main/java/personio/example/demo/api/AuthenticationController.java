package personio.example.demo.api;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import personio.example.demo.model.CreateAdmin;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.response.CreateOrgResponse;
import personio.example.demo.service.AuthenticationService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/api/v1/createAdminUser")
    @ResponseBody
    public ResponseEntity<String> createAdminUser(@NonNull @RequestBody CreateAdmin admin) {
        boolean adminUser = authenticationService.createNewAdminUser(admin);
        if (adminUser) return ResponseEntity.ok("User created");
        else return ResponseEntity.badRequest().body("Error in creating user");
    }

    @PostMapping("/api/v1/authenticateUser")
    @ResponseBody
    public ResponseEntity<String> authenticateUser(@NonNull @RequestBody CreateAdmin admin) {
        Optional<String> session = authenticationService.authenticateUser(admin);
        if (session.isPresent()) return ResponseEntity.ok(session.get());
        else return ResponseEntity.badRequest().body("Error in authenticating user");
    }
}
