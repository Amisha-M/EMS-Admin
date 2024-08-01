package com.admin.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.AdminMapper;
import com.admin.bo.AdminBO;
import com.admin.client.AdminClient;
import com.admin.helper.RestHelper;
import com.admin.util.Constants;

@Service
public class AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private RestHelper restHelper;

    @Autowired
    private AdminClient adminClient;

    @Autowired
    private AdminBO adminBO;
    
    private final AdminMapper adminMapper = AdminMapper.INSTANCE;

    
//    @Autowired
//    private RestTemplate restTemplate;

    //USING REST 
    public String getEmployeesUsingRestTemplate() {
        logger.info(Constants.GET_EMPLOYEES_REST_LOG);
        String response = restHelper.getEmployees();
        return response; // Assuming the response is already a String representation
    }
    
    public AdminBO createEmployeeUsingRestTemplate(AdminBO adminBO) {
        logger.info(Constants.CREATE_EMPLOYEE_REQUEST_LOG, adminBO);
        AdminBO createdEmployee = restHelper.createEmployee(adminBO);
        logger.info(Constants.CREATE_EMPLOYEE_RESPONSE_LOG, createdEmployee);
        return createdEmployee;
    }

    public AdminBO getEmployeeByIdUsingRestTemplate(Long id) {
        logger.info(Constants.GET_EMPLOYEE_BY_ID_REQUEST_LOG, id);
        AdminBO employee = restHelper.getEmployeeById(id);
        logger.info(Constants.EMPLOYEE_RETRIEVED_LOG, employee);
        return employee;
    }
    
//    public List<AdminEntity> getAllEmp(){
//    	ResponseEntity<List<AdminEntity>> response = restTemplate.exchange
//    			("http://localhost:8080/employees/display", 
//    					HttpMethod.GET, null, new ParameterizedTypeReference<List<AdminEntity>>(){});
//    	return response.getBody();
//    }
//    
    
    /*
     * USING FEIGN CLIENT
     * Defines the Feign client with methods to call external services.
	 * Includes logging for each method to trace requests and responses.
     */
    
    public String getEmployeesUsingFeign() {
        logger.info(Constants.GET_EMPLOYEES_FEIGN_REQUEST_LOG);
        String employees = adminClient.getEmployeesUsingFeign();
        logger.info("Service - Received response from Feign client: {}", employees);
        return employees;
    }

    public AdminBO createEmployee(AdminBO adminBO) {
        logger.info(Constants.CREATE_EMPLOYEE_FEIGN_REQUEST_LOG, adminBO);
        AdminBO createdEmployee = adminClient.createEmployeeUsingFeign(adminBO);
        logger.info(Constants.CREATE_EMPLOYEE_FEIGN_RESPONSE_LOG, createdEmployee);
        return createdEmployee;
    }

    public AdminBO getEmployeeById(Long id) {
        logger.info(Constants.GET_EMPLOYEE_BY_ID_FEIGN_REQUEST_LOG, id);
        AdminBO employee = adminClient.getEmployeeByIdUsingFeign(id);
        logger.info(Constants.EMPLOYEE_RETRIEVED_LOG, employee);
        return employee;
    }
	
}
