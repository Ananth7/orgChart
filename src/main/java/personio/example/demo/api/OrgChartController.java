package personio.example.demo.api;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import personio.example.demo.model.OrgChart;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.response.CreateOrgResponse;
import personio.example.demo.service.OrgChartService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class OrgChartController {

    @Autowired
    private final OrgChartService orgChartService;

    @PostMapping("api/v1/postOrgChart")
    public ResponseEntity<Optional<OrgChart>> postOrgChart(@NonNull @RequestBody CreateOrgChartRequest createOrgChartRequest) throws URISyntaxException {
        CreateOrgResponse orgChart = orgChartService.createOrgChart(createOrgChartRequest);
        if (orgChart.getOrgChart().isPresent()) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(orgChart.getOrgChart()).toUri();
            return ResponseEntity.created(uri).body(orgChart.getOrgChart());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
