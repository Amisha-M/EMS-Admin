package com.admin.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.admin.bo.AdminBO;
import com.admin.client.AdminClient;
import com.admin.entity.AdminEntity;
import com.admin.helper.RestHelper;

@Service
public class AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private RestHelper restHelper;

    @Autowired
    private AdminClient adminClient;

    @Autowired
    private AdminBO adminBO;
    
//    @Autowired
//    private RestTemplate restTemplate;

    public String getEmployeesUsingRestTemplate() {
        logger.info("Retrieving employees using RestTemplate");
        String response = restHelper.getEmployees();
        return adminBO.processEmployees(response);
    }
    
//    public List<AdminEntity> getAllEmp(){
//    	ResponseEntity<List<AdminEntity>> response = restTemplate.exchange
//    			("http://localhost:8080/employees/display", 
//    					HttpMethod.GET, null, new ParameterizedTypeReference<List<AdminEntity>>(){});
//    	return response.getBody();
//    }

    public String getEmployeesUsingFeign() {
        logger.info("Retrieving employees using Feign Client");
        String response = adminClient.getEmployees();
        return adminBO.processEmployees(response);
    }
	
}
