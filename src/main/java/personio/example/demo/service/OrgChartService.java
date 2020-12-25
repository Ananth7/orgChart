package personio.example.demo.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import personio.example.demo.model.OrgChart;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.response.CreateOrgResponse;

import java.util.Optional;

@Service
public class OrgChartService {

    public CreateOrgResponse createOrgChart(@NonNull CreateOrgChartRequest orgChartRequest) {
        return new CreateOrgResponse(Optional.empty(), "Dummy message");
    }
}
