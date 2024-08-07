package com.admin.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import com.admin.entity.AdminEntity;

@Component
public class RestHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(RestHelper.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;

//    public RestHelper(RestTemplate restTemplate, @Value("${employee.api.url}") String baseUrl) {
//        this.restTemplate = restTemplate;
//        this.baseUrl = baseUrl;
//    }
    
    public RestHelper(RestTemplate restTemplate, @Value("${ems.provider.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public String getEmployees() {
        logger.info("Calling GET /employees from RestTemplate");
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/employees/display", String.class);
        return response.getBody();
    }

    
    public AdminEntity createEmployee(AdminEntity adminEntity) {
        logger.info("Calling POST {} with body: {}", baseUrl + "/employees/create", adminEntity);
        try {
            ResponseEntity<AdminEntity> response = restTemplate.postForEntity(baseUrl + "/employees/create", adminEntity, AdminEntity.class);
            logger.info("Response received for POST {}: Status={}, Body={}", baseUrl + "/employees/create", response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("Error occurred while calling POST {}: {}", baseUrl + "/employees/create", e.getMessage(), e);
            throw e;
        }
    }

    public AdminEntity getEmployeeById(Long id) {
        logger.info("Calling GET {} with ID: {}", baseUrl + "/employees/id/{id}" , id);
//        String url = baseUrl+"/employees/id/{id}";
//        return restTemplate.getForObject(url, AdminEntity.class,id);
        try {
            ResponseEntity<AdminEntity> response = restTemplate.getForEntity(baseUrl + "/employees/id/{id}" , AdminEntity.class);
            logger.info("Response received for GET {}: Status={}, Body={}", baseUrl + "/employees/id/{id}" , response.getStatusCode(), response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("Error occurred while calling GET {}: {}", baseUrl + "/employees/id/{id}" , e.getMessage(), e);
            throw e;
        }
    }

}
