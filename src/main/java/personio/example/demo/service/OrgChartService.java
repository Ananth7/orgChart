package personio.example.demo.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personio.example.demo.dao.OrgChartDao;
import personio.example.demo.model.OrgChart;
import personio.example.demo.request.CreateOrgChartRequest;
import personio.example.demo.response.CreateOrgResponse;

import java.util.Optional;

@Service
public class OrgChartService {

    @Autowired
    private OrgChartDao orgChartDao;

    public CreateOrgResponse createOrgChart(@NonNull CreateOrgChartRequest orgChartRequest) {
        return new CreateOrgResponse(Optional.empty(), "Dummy message");
    }

    public OrgChart getOrgChart() {
        return new OrgChart();
    }

}
