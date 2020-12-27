package personio.example.demo.api;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.response.CreateOrgResponse;
import personio.example.demo.response.GetManagersResponse;
import personio.example.demo.service.OrgChartService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@AllArgsConstructor
public class OrgChartController {

    @Autowired
    private final OrgChartService orgChartService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrgChartController.class);

    @PostMapping("/api/v1/postOrgChart")
    @ResponseBody
    public ResponseEntity<String> postOrgChart(@NonNull @RequestBody Map<String, String> createOrgChartRequest) throws URISyntaxException {

        for (String key: createOrgChartRequest.keySet())
            LOGGER.info("Got request {}: {}", key, createOrgChartRequest.get(key));
        CreateOrgResponse orgChart = orgChartService.createOrgChart(new CreateOrgChartRequest(createOrgChartRequest));
        if (orgChart.getOrgChart().isPresent()) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(orgChart.getOrgChart()).toUri();
            LOGGER.info("Org chart successfully created. {}", orgChart.getOrgChart());
            return ResponseEntity.created(uri).body(orgChart.getOrgChart().get());
        } else {
            LOGGER.info("Error in creating org chart. {}", orgChart.getMessage());
            return ResponseEntity.badRequest().body(orgChart.getMessage());
        }
    }

    @GetMapping("/api/v1/getOrgChart")
    @ResponseBody
    public ResponseEntity<String> getOrgChart() {
        return ResponseEntity.ok(orgChartService.getOrgChartFromDB());
    }

    @GetMapping("/api/v1/getManagers")
    @ResponseBody
    public ResponseEntity<GetManagersResponse> getManagers(@RequestParam("employee") String employee) {
        return ResponseEntity.ok(orgChartService.getManagersForEmployee(employee));
    }

}
