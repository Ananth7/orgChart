package personio.example.demo.validations;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import personio.example.demo.model.OrgChart;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.request.OrgChartValidationState;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    private static OrgChart orgChart1, orgChart2, orgChart3;
    private static CreateOrgChartRequest createOrgChartRequest1, createOrgChartRequest2, createOrgChartRequest3;
    private static Map<String, String> validInput1;
    private static Map<String, String> invalidInput1;
    private static Map<String, String> invalidInput2;


    @BeforeAll
    static void setUp() {
        validInput1 = new HashMap<>();
        invalidInput1 = new HashMap<>();
        invalidInput2 = new HashMap<>();
        validInput1.put("1", "2");
        validInput1.put("3", "2");
        invalidInput1.put("1", "2");
        invalidInput1.put("2", "1");
        invalidInput2.put("2", "1");
        invalidInput2.put("5", "3");
        createOrgChartRequest1 = new CreateOrgChartRequest(validInput1);
        createOrgChartRequest2 = new CreateOrgChartRequest(invalidInput1);
        createOrgChartRequest3 = new CreateOrgChartRequest(invalidInput2);
        orgChart1 = new OrgChart(createOrgChartRequest1);
        orgChart2 = new OrgChart(createOrgChartRequest2);
        orgChart3 = new OrgChart(createOrgChartRequest3);
    }

    @Test
    void validateOrgChartRequest() {
        assertEquals(ValidationUtils.validateOrgChartRequest(createOrgChartRequest1, orgChart1), OrgChartValidationState.VALID);
        assertEquals(ValidationUtils.validateOrgChartRequest(createOrgChartRequest2, orgChart2), OrgChartValidationState.INVALID_LOOP);
        assertEquals(ValidationUtils.validateOrgChartRequest(createOrgChartRequest3, orgChart3), OrgChartValidationState.INVALID_DISCONNECTED_ORG);
    }
}