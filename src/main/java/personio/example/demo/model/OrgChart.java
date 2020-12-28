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
    /**
     * orgGraph represent the structure of the org in a graph format. The key is the name of the employee
     * and the value is a set of employees who report to him/her (reportees)
     */
    Map<String, Set<String>> orgGraph;

    /**
     * this field is used in the Disjoint set union-find algorithm
     */
    Map<String, String> parent;

    /**
     * This field keeps track of the indegree of each employee. The CEO (or) boss has no one above him so the indegree is 0
     */
    Map<String, Integer> inDegree;

    /**
     * Set of all employees in the organization
     */
    Set<String> employees;

    /**
     * The CEO
     */
    String boss;

    /**
     * This method returns the org chart in the desired format.
     * It makes use of DFS graph traversal algorithm to recursively build the output in the desired format.
     */
    public JSONObject getStructuredOrgChart(String employee) {
        System.out.println("Employee = " + employee);
        JSONObject org = new JSONObject();

        // base case
        if (! orgGraph.containsKey(employee)) {
            org.put(employee, new JSONObject());
            return org;
        }

        JSONObject reporteeOrg = new JSONObject();
        for (String reportee: orgGraph.get(employee)) {
            reporteeOrg.put(reportee, getStructuredOrgChart(reportee).get(reportee));
        }
        org.put(employee, reporteeOrg);
        return org;
    }

    public OrgChart(@NonNull CreateOrgChartRequest orgChartRequest) {
        this.orgGraph = makeGraph(orgChartRequest);
    }

    /**
     * This method is used to build the graph.
     * It also populates all the other necessary fields for the rest of the algorithms.
     */
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

    /**
     * This method runs a union-find disjoint set algorithm on top of the built graph to identify
     * the number of separate components in the graph.
     * All connected components appear under the same disjoint set.
     */
    public boolean validateForMultipleComponents() {
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

    /**
     * Utility method for Union-find
     */
    private void setParents() {
        for (String employee: orgGraph.keySet()) {
            parent.put(employee, employee);
            for (String reportee: orgGraph.get(employee))
                parent.put(reportee, reportee);
        }
    }

    /**
     * This method uses graph coloring technique along with DFS to identify loops in the graph data structure.
     * The way it does it by maintaining a stack and if a node in progress (GRAY) node comes up as a neighbour
     * of the current nodes being processed. It means there is a cyclic dependency in the graph.
     */
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

    /**
     * Method to make use of isCyclic method and determine if there are any loops in the given createOrgRequest
     */
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

    /**
     * Methods for union-find algorithm
     */
    private void union(String a, String b) {
        String parenta = findParent(a);
        String parentb = findParent(b);
        parent.put(parenta, parentb);
    }

    /**
     * Methods for union-find algorithm
     */
    private String findParent(String a) {
        while (!parent.get(a).equals(a)) {
            a = parent.get(a);
        }
        return a;
    }
}
