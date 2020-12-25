package personio.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import personio.example.demo.model.OrgChart;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Data
public class CreateOrgResponse {
    private Optional<OrgChart> orgChart;
    private String message;
}
