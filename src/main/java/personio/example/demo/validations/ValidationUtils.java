package personio.example.demo.validations;

import lombok.NonNull;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.request.OrgChartValidationState;

public class ValidationUtils {
    public static OrgChartValidationState validateOrgChartRequest(@NonNull CreateOrgChartRequest orgChartRequest) {
        Graph graph = new Graph(orgChartRequest);

        if (!graph.validateForLoops(orgChartRequest)) return OrgChartValidationState.INVALID_LOOP;
//        validateForMultipleRoots(orgChartRequest);
        if (!graph.validateForMultipleComponents(orgChartRequest)) return OrgChartValidationState.INVALID_DISCONNECTED_ORG;
        return OrgChartValidationState.VALID;
    }

}
