package personio.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.StringJoiner;

@AllArgsConstructor
@Data
public class CreateOrgChartRequest {
    Map<String, String> employeeRelationships;

    @Override
    public String toString() {
        return new StringJoiner(", ", CreateOrgChartRequest.class.getSimpleName() + "[", "]")
                .add("employeeRelationships=" + employeeRelationships)
                .toString();
    }
}
