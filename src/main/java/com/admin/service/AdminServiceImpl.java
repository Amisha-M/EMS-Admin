package com.admin.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.AdminMapper;
import com.admin.bo.AdminBO;
import com.admin.client.AdminClient;
import com.admin.entity.AdminEntity;
import com.admin.helper.RestHelper;
import com.admin.util.Constants;

@Service
public class AdminServiceImpl implements AdminService{

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private RestHelper restHelper;

    @Autowired
    private AdminClient adminClient;

    @Autowired
    private AdminBO adminBO;
    
    @Autowired
    private AdminMapper adminMapper;
    
//    @Autowired
//    private RestTemplate restTemplate;

    //USING REST 
    
    public String getEmployeesUsingRestTemplate() {
        logger.info(Constants.GET_EMPLOYEES_REST_LOG);
        return restHelper.getEmployees();
    }

    public AdminBO createEmployeeUsingRestTemplate(AdminBO adminBO) {
        logger.info(Constants.CREATE_EMPLOYEE_REQUEST_LOG, adminBO);
        AdminEntity entity = adminMapper.toEntity(adminBO);
        AdminEntity createdEntity = restHelper.createEmployee(entity);
        AdminBO createdBO = adminMapper.toBO(createdEntity);
        logger.info(Constants.CREATE_EMPLOYEE_RESPONSE_LOG, createdBO);
        return createdBO;
    }

    public AdminBO getEmployeeByIdUsingRestTemplate(Long id) {
        logger.info(Constants.GET_EMPLOYEE_BY_ID_REQUEST_LOG, id);
        AdminEntity entity = restHelper.getEmployeeById(id);
        AdminBO employee = adminMapper.toBO(entity);
        logger.info(Constants.EMPLOYEE_RETRIEVED_LOG, employee);
        return employee;
    }
    
    /*
     * USING FEIGN CLIENT
     * Defines the Feign client with methods to call external services.
	 * Includes logging for each method to trace requests and responses.
     */
    
    
    public String getEmployeesUsingFeign() {
        logger.info(Constants.GET_EMPLOYEES_FEIGN_REQUEST_LOG);
        return adminClient.getEmployeesUsingFeign();
    }

    public AdminBO createEmployeeUsingFeign(AdminBO adminBO) {
        logger.info(Constants.CREATE_EMPLOYEE_FEIGN_REQUEST_LOG, adminBO);
        AdminEntity entity = adminMapper.toEntity(adminBO);
        AdminEntity createdEntity = adminClient.createEmployeeUsingFeign(entity);
        AdminBO createdBO = adminMapper.toBO(createdEntity);
        logger.info(Constants.CREATE_EMPLOYEE_FEIGN_RESPONSE_LOG, createdBO);
        return createdBO;
    }

    public AdminBO getEmployeeByIdUsingFeign(Long id) {
        logger.info(Constants.GET_EMPLOYEE_BY_ID_FEIGN_REQUEST_LOG, id);
        AdminEntity entity = adminClient.getEmployeeByIdUsingFeign(id);
        AdminBO employee = adminMapper.toBO(entity);
        logger.info(Constants.EMPLOYEE_RETRIEVED_LOG, employee);
        return employee;
    }
	
}
