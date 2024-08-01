package com.admin.util;

public class Constants {
	
	// REST Template Logging Messages
    public static final String GET_EMPLOYEES_REST_LOG = "Retrieving employees using RestTemplate";
    public static final String CREATE_EMPLOYEE_REQUEST_LOG = "Received request to create employee: {}";
    public static final String CREATE_EMPLOYEE_RESPONSE_LOG = "Response after creating employee: {}";
    public static final String GET_EMPLOYEE_BY_ID_REQUEST_LOG = "Received request to retrieve employee by ID: {}";
    public static final String EMPLOYEE_RETRIEVED_LOG = "Employee retrieved: {}";
    public static final String EMPLOYEE_NOT_FOUND_LOG = "Employee with ID {} not found";

    // Feign Client Logging Messages
    public static final String GET_EMPLOYEES_FEIGN_REQUEST_LOG = "Received request to get all employees using Feign";
    public static final String GET_EMPLOYEES_FEIGN_CLIENT_ERROR_LOG = "Error retrieving employees using Feign: {}";
    public static final String CREATE_EMPLOYEE_FEIGN_REQUEST_LOG = "Received request to create employee: {}";
    public static final String CREATE_EMPLOYEE_FEIGN_RESPONSE_LOG = "Response after creating employee: {}";
    public static final String GET_EMPLOYEE_BY_ID_FEIGN_REQUEST_LOG = "Received request to get employee by ID using Feign: {}";
    public static final String ERROR_RETRIEVING_EMPLOYEE_BY_ID_FEIGN_LOG = "Error retrieving employee by ID {} using Feign: {}";

    // General Messages
    public static final String SERVICE_ERROR_LOG = "Service encountered an error: {}";
    public static final String FEIGN_CLIENT_ERROR_LOG = "Feign client error: {}";
    public static final String INVALID_EMPLOYEE_DATA_LOG = "Invalid employee data provided: {}";


}
