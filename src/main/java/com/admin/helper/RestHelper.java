package com.admin.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(RestHelper.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public RestHelper(RestTemplate restTemplate, @Value("${employee.api.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public String getEmployees() {
        logger.info("Calling GET /employees from RestTemplate");
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/employees/display", String.class);
        return response.getBody();
    }

}
