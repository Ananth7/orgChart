package personio.example.demo.validations;

import lombok.NonNull;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.request.OrgChartValidationState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ValidationUtils {
    public static OrgChartValidationState validateOrgChartRequest(@NonNull CreateOrgChartRequest orgChartRequest) {
        if (!validateForLoops(orgChartRequest)) return OrgChartValidationState.INVALID_LOOP;
//        validateForMultipleRoots(orgChartRequest);
        return OrgChartValidationState.VALID;
    }

    public static boolean validateForLoops(@NonNull CreateOrgChartRequest orgChartRequest) {
        Map<String, Set<String>> graph = makeGraph(orgChartRequest);
        Map<String, VisitedState> visited = new HashMap<>();
        for (String employee: orgChartRequest.getEmployeeRelationships().keySet()) {
            visited.put(employee, VisitedState.WHITE);
            visited.put(orgChartRequest.getEmployeeRelationships().get(employee), VisitedState.WHITE);
        }

        for (String employee: visited.keySet()) {
            if (visited.get(employee).equals(VisitedState.BLACK)) continue;
            if (isCyclic(employee, visited, graph)) return false;
        }
        return true;
    }

    private static Map<String, Set<String>> makeGraph(@NonNull CreateOrgChartRequest orgChartRequest) {
        Map<String, Set<String>> orgGraph = new HashMap<>();
        for (String employee: orgChartRequest.getEmployeeRelationships().keySet()) {
            String manager = orgChartRequest.getEmployeeRelationships().get(employee);
            if (! orgGraph.containsKey(manager)) orgGraph.put(manager, new HashSet<String>());
            orgGraph.get(manager).add(employee);
        }
        return orgGraph;
    }

    private static boolean isCyclic(@NonNull String employee,
                                    @NonNull Map<String, VisitedState> visited,
                                    @NonNull Map<String, Set<String>> graph) {
        if (visited.containsKey(employee) && visited.get(employee).equals(VisitedState.GRAY)) return true;
        if (visited.containsKey(employee) && visited.get(employee).equals(VisitedState.BLACK)) return false;
        visited.put(employee, VisitedState.GRAY);
        if (graph.containsKey(employee))
            for (String reportee: graph.get(employee)) {
                if (isCyclic(reportee, visited, graph)) return true;
            }
        visited.put(employee, VisitedState.BLACK);
        return false;
    }

    private enum VisitedState {
        WHITE,
        GRAY,
        BLACK
    }

}
