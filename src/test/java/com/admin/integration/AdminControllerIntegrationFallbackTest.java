package com.admin.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerIntegrationFallbackTest {

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance().build();

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setup() {
        WireMock.configureFor("localhost", wireMockExtension.getPort());
        WireMock.reset();

        // Simulate service failure
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/employees/display"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withBody("Internal Server Error")));
    }

    @Test
    void testGetEmployeesUsingRestTemplateFallback() {
        ResponseEntity<String> response = restTemplate.getForEntity("/client/rest", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Fallback response for getEmployeesUsingRestTemplate", response.getBody());
    }
}
