package personio.example.demo.model;

import lombok.Data;
import lombok.NonNull;
import personio.example.demo.request.CreateOrgChartRequest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;


/**
 * This class contains all the utilities for validating the org structure
 * This class implements graph algorithms
 * It can be implemented as a Type parameterized class to make the Type Generic in the future should the need arise.
 */
@Data
public class OrgChart {
    Map<String, Set<String>> orgGraph = new HashMap<>();
    Map<String, String> parent;
    Map<String, Integer> inDegree;
    Set<String> employees;
    String boss;

    public JSONObject getStructuredOrgChart(String employee) {
        System.out.println("Employee = " + employee);
        JSONObject org = new JSONObject();
        if (! orgGraph.containsKey(employee)) {
            org.put(employee, "");
            return org;
        }
        for (String reportee: orgGraph.get(employee)) {
            org.append(employee, getStructuredOrgChart(reportee));
        }
        return org;
    }

    public OrgChart(@NonNull CreateOrgChartRequest orgChartRequest) {
        this.orgGraph = makeGraph(orgChartRequest);
    }

    private Map<String, Set<String>> makeGraph(@NonNull CreateOrgChartRequest orgChartRequest) {
        Map<String, Set<String>> orgGraph = new HashMap<>();
        employees = new HashSet<>();
        parent = new HashMap<>();
        inDegree = new HashMap<>();
        for (String employee: orgChartRequest.getEmployeeRelationships().keySet()) {
            if (! inDegree.containsKey(employee)) inDegree.put(employee, 1);
            else inDegree.put(employee, inDegree.get(employee) + 1);

            String manager = orgChartRequest.getEmployeeRelationships().get(employee);
            if (! orgGraph.containsKey(manager)) orgGraph.put(manager, new HashSet<String>());
            orgGraph.get(manager).add(employee);
            employees.add(employee);
            employees.add(manager);
        }

        for (String employee: employees) {
            if (! inDegree.containsKey(employee)) { boss = employee; break;}
        }

        return orgGraph;
    }

    public boolean validateForMultipleComponents(@NonNull CreateOrgChartRequest orgChartRequest) {
        setParents();
        for (String employee: orgGraph.keySet()) {
            for (String reportee: orgGraph.get(employee)) {
                union(employee, reportee);
            }
        }
        int count = 0;
        String unionparent = null;
        for (String employee: employees) {
            String unionset = findParent(employee);
            if (unionparent == null) unionparent = unionset;
            if (!unionparent.equals(unionset)) return false;
            count ++;
        }
        return count == employees.size();
    }

    private void setParents() {
        for (String employee: orgGraph.keySet()) {
            parent.put(employee, employee);
            for (String reportee: orgGraph.get(employee))
                parent.put(reportee, reportee);
        }
    }

    private boolean isCyclic(@NonNull String employee,
                             @NonNull Map<String, VisitedState> visited) {
        if (visited.containsKey(employee) && visited.get(employee).equals(VisitedState.GRAY)) return true;
        if (visited.containsKey(employee) && visited.get(employee).equals(VisitedState.BLACK)) return false;
        visited.put(employee, VisitedState.GRAY);
        if (orgGraph.containsKey(employee))
            for (String reportee: orgGraph.get(employee)) {
                if (isCyclic(reportee, visited)) return true;
            }
        visited.put(employee, VisitedState.BLACK);
        return false;
    }

    public boolean validateForLoops(@NonNull CreateOrgChartRequest orgChartRequest) {
        Map<String, VisitedState> visited = new HashMap<>();
        for (String employee: orgChartRequest.getEmployeeRelationships().keySet()) {
            visited.put(employee, VisitedState.WHITE);
            visited.put(orgChartRequest.getEmployeeRelationships().get(employee), VisitedState.WHITE);
        }

        for (String employee: visited.keySet()) {
            if (visited.get(employee).equals(VisitedState.BLACK)) continue;
            if (isCyclic(employee, visited)) return false;
        }
        return true;
    }

    public enum VisitedState {
        WHITE,
        GRAY,
        BLACK
    }

    private void union(String a, String b) {
        String parenta = findParent(a);
        String parentb = findParent(b);
        parent.put(parenta, parentb);
    }

    private String findParent(String a) {
        while ( parent.get(a) != a) {
            a = parent.get(a);
        }
        return a;
    }
}
