package personio.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@AllArgsConstructor
@Data
public class CreateOrgResponse {
    private Optional<String> orgChart;
    private String message;
}
