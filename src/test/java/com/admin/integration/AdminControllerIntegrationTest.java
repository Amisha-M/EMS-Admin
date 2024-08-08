package com.admin.integration;

//import com.github.tomakehurst.wiremock.client.WireMockClient;
import com.admin.bo.AdminBO;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
//import com.github.tomakehurst.wiremock.server.WireMockServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerIntegrationTest {

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance().build();

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setup() {
        WireMock.configureFor("localhost", wireMockExtension.getPort());
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/employees/display"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("[{\"id\":1,\"name\":\"Amar Singh\",\"position\":\"Developer\",\"salary\":80000}]")));
        
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/employees/create"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withBody("{\"id\":1,\"name\":\"Amar Singh\",\"position\":\"Developer\",\"salary\":80000}")));

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/employees/id/1"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("{\"id\":1,\"name\":\"Amar Singh\",\"position\":\"Developer\",\"salary\":80000}")));
    }

    @Test
    void testGetEmployeesUsingRestTemplate() {
        ResponseEntity<String> response = restTemplate.getForEntity("/client/rest", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
//        System.out.println(response.getBody());
        assertTrue(response.getBody().contains("Amar Singh"));
    }

    @Test
    void testCreateEmployee() {
        AdminBO adminBO = new AdminBO(1L, "Amar Singh", "Developer", 80000L);
        ResponseEntity<AdminBO> response = restTemplate.postForEntity("/client/rest/employees", adminBO, AdminBO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Amar Singh", response.getBody().getName());
    }

    @Test
    void testGetEmployeeById() {
        ResponseEntity<AdminBO> response = restTemplate.getForEntity("/client/rest/employees/1", AdminBO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Amar Singh", response.getBody().getName());
    }
    
    @Test
    void testGetEmployeesUsingFeign() {
        ResponseEntity<String> response = restTemplate.getForEntity("/client/feign", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Amar Singh"));
    }

    @Test
    void testCreateEmployeeUsingFeign() {
        AdminBO adminBO = new AdminBO(1L, "Amar Singh", "Developer", 80000L);
        ResponseEntity<AdminBO> response = restTemplate.postForEntity("/client/feign/employees", adminBO, AdminBO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Amar Singh", response.getBody().getName());
    }

    @Test
    void testGetEmployeeByIdUsingFeign() {
        ResponseEntity<AdminBO> response = restTemplate.getForEntity("/client/feign/employees/1", AdminBO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Amar Singh", response.getBody().getName());
    }
}
