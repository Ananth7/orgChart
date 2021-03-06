package personio.example.demo.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import personio.example.demo.model.Admin;
import personio.example.demo.service.AuthenticationService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.UUID;


@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postingANewAValidAdminUserShouldCreateANewAdminUser() throws Exception {
        Mockito.when(authenticationService.createNewAdminUser(Mockito.any(Admin.class))).thenReturn(true);
        Admin admin = new Admin(UUID.randomUUID().toString(), "password");
        this.mockMvc.perform(post("/api/v1/createAdminUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User created")));
    }

    @Test
    public void postingInvalidUsersShouldFail() throws Exception {
        Admin admin = new Admin(UUID.randomUUID().toString(), "password");
        Mockito.when(authenticationService.createNewAdminUser(Mockito.any(Admin.class))).thenReturn(true);
        this.mockMvc.perform(post("/api/v1/createAdminUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User created")));
        Mockito.when(authenticationService.createNewAdminUser(Mockito.any(Admin.class))).thenReturn(false);
        this.mockMvc.perform(post("/api/v1/createAdminUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(admin)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Error in creating user")));
    }

    @Test
    public void authenticatingAValidSessionShouldSucceed() throws Exception {
        String sessionId = UUID.randomUUID().toString();
        Admin admin = new Admin(UUID.randomUUID().toString(), "password");
        Mockito.when(authenticationService.authenticateUser(Mockito.any(Admin.class))).thenReturn(Optional.of(sessionId));
        this.mockMvc.perform(post("/api/v1/authenticateUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(sessionId)));
    }

    @Test
    public void authenticatingAnInvalidSessionShouldFail() throws Exception {
        String sessionId = UUID.randomUUID().toString();
        Admin admin = new Admin(UUID.randomUUID().toString(), "password");
        Mockito.when(authenticationService.authenticateUser(Mockito.any(Admin.class))).thenReturn(Optional.empty());
        this.mockMvc.perform(post("/api/v1/authenticateUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(admin)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Error in authenticating user")));
    }

}
