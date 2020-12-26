package personio.example.demo.validations;

import lombok.NonNull;
import personio.example.demo.model.OrgChart;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.request.OrgChartValidationState;

public class ValidationUtils {
    public static OrgChartValidationState validateOrgChartRequest(@NonNull CreateOrgChartRequest orgChartRequest, OrgChart orgChart) {

        if (!orgChart.validateForLoops(orgChartRequest)) return OrgChartValidationState.INVALID_LOOP;
//        validateForMultipleRoots(orgChartRequest);
        if (!orgChart.validateForMultipleComponents(orgChartRequest)) return OrgChartValidationState.INVALID_DISCONNECTED_ORG;
        return OrgChartValidationState.VALID;
    }

}
