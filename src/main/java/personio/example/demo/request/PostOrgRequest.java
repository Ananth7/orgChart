package personio.example.demo.request;

import lombok.Data;
import personio.example.demo.model.Session;

import java.util.Map;

@Data
public class PostOrgRequest {
    private Session session;
    private Map<String, String> createOrgChartRequest;
}
