package personio.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import personio.example.demo.api.AuthenticationController;
import personio.example.demo.api.OrgChartController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private OrgChartController orgChartController;

    @Test
    public void testControllerSanity() {
        assertThat(authenticationController).isNotNull();
        assertThat(orgChartController).isNotNull();
    }
}
