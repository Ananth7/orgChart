package personio.example.demo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import personio.example.demo.model.Admin;
import personio.example.demo.model.OrgChart;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.request.OrgChartValidationState;
import personio.example.demo.response.CreateOrgResponse;
import personio.example.demo.response.GetManagersResponse;
import personio.example.demo.service.AuthenticationService;
import personio.example.demo.service.OrgChartService;
import personio.example.demo.validations.ValidationUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrgChartController.class)
class OrgChartControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private OrgChartService orgChartService;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private MockMvc mockMvc;

    private static OrgChart orgChart1;
    private static CreateOrgChartRequest createOrgChartRequest1;
    private static Map<String, String> validInput1;

    @BeforeAll
    static void setUp() {
        validInput1 = new HashMap<>();
        validInput1.put("1", "2");
        validInput1.put("3", "2");
        createOrgChartRequest1 = new CreateOrgChartRequest(validInput1);
        orgChart1 = new OrgChart(createOrgChartRequest1);
    }

    @Test
    void postOrgChart() throws Exception {
        CreateOrgResponse createOrgResponse = new CreateOrgResponse(Optional.of("{\"2\":{\"1\":{},\"3\":{}}}"), "Successfully created org");
        Mockito.when(authenticationService.authenticateSession(Mockito.any(String.class))).thenReturn(true);
        Mockito.when(orgChartService.createOrgChart(Mockito.any(CreateOrgChartRequest.class))).thenReturn(createOrgResponse);
        this.mockMvc.perform(post("/api/v1/postOrgChart")
                .header("sessionId", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validInput1)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("{\"2\":{\"1\":{},\"3\":{}}}")));
    }

    @Test
    void getOrgChart() throws Exception {
        CreateOrgResponse createOrgResponse = new CreateOrgResponse(Optional.of("{\"2\":{\"1\":{},\"3\":{}}}"), "Successfully created org");
        Mockito.when(authenticationService.authenticateSession(Mockito.any(String.class))).thenReturn(true);
        Mockito.when(orgChartService.getOrgChartFromDB()).thenReturn(createOrgResponse.getOrgChart().toString());
        this.mockMvc.perform(get("/api/v1/getOrgChart")
                .header("sessionId", UUID.randomUUID().toString()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("{\"2\":{\"1\":{},\"3\":{}}}")));
    }

    @Test
    void getManagers() throws Exception {
        Mockito.when(authenticationService.authenticateSession(Mockito.any(String.class))).thenReturn(true);
        GetManagersResponse getManagersResponse = new GetManagersResponse("1", "2");
        Mockito.when(orgChartService.getManagersForEmployee(Mockito.anyString())).thenReturn(getManagersResponse);
        this.mockMvc.perform(get("/api/v1/getManagers")
                .header("sessionId", UUID.randomUUID().toString()).queryParam("employee", "1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("{\"manager\":\"1\", \"skipLevelManager\":\"2\"}")));
    }
}