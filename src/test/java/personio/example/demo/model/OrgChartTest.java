package personio.example.demo.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import personio.example.demo.request.CreateOrgChartRequest;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

class OrgChartTest {

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
    void getStructuredOrgChart() {
        assertEquals(orgChart1.getStructuredOrgChart(orgChart1.getBoss()).toString(), "{\"2\":{\"1\":{},\"3\":{}}}");
    }

    @Test
    void validateForMultipleComponents_falsecase() {
        assertTrue(orgChart1.validateForMultipleComponents());
    }

    @Test
    void validateForMultipleComponents_truecase() {
        assertFalse(orgChart3.validateForMultipleComponents());
    }

    @Test
    void validateForLoops_falsecase() {
        assertTrue(orgChart1.validateForLoops(createOrgChartRequest1));
    }

    @Test
    void validateForLoops_truecase() {
        assertFalse(orgChart2.validateForLoops(createOrgChartRequest3));
    }

}