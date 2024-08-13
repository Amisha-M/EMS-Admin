package com.admin.controller;

import java.util.ArrayList;
import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.bo.AdminBO;
import com.admin.entity.AdminEntity;
import com.admin.service.AdminServiceImpl;
import com.admin.util.Constants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/client")
@Validated
@Tag(name = "Admin Controller", description = "API for Admin operations using RestTemplate and Feign Client") /*http://localhost:8089/v3/api-docs || http://localhost:8089/swagger-ui.html*/
public class AdminController {
	
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
    private AdminServiceImpl adminService;

    @Operation(summary = "Retrieve employees using RestTemplate", description = "This API retrieves a list of employees using RestTemplate.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved employees",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error retrieving employees",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/rest")
    public String getEmployeesUsingRestTemplate() {
        logger.info(Constants.GET_EMPLOYEES_REST_LOG);
        return adminService.getEmployeesUsingRestTemplate();
    }

    @Operation(summary = "Create an employee using RestTemplate", description = "This API creates a new employee using RestTemplate.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminBO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error creating employee",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("rest/employees")
    public ResponseEntity<AdminBO> createEmployee(@RequestBody @Valid AdminBO adminBO) {
        logger.info(Constants.CREATE_EMPLOYEE_REQUEST_LOG, adminBO);
        AdminBO createdEmployee = adminService.createEmployeeUsingRestTemplate(adminBO);
        logger.info(Constants.CREATE_EMPLOYEE_RESPONSE_LOG, createdEmployee);
        return ResponseEntity.status(201).body(createdEmployee);
    }

    @Operation(summary = "Retrieve an employee by ID using RestTemplate", description = "This API retrieves an employee by their ID using RestTemplate.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved employee",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminBO.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error retrieving employee",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("rest/employees/{id}")
    public ResponseEntity<AdminBO> getEmployeeById(
            @Parameter(description = "ID of the employee to be retrieved", required = true)
            @PathVariable("id") @NotNull Long id
    ) {
        logger.info(Constants.GET_EMPLOYEE_BY_ID_REQUEST_LOG, id);
        AdminBO employee = adminService.getEmployeeByIdUsingRestTemplate(id);
        if (employee != null) {
            logger.info(Constants.EMPLOYEE_RETRIEVED_LOG, employee);
            return ResponseEntity.ok(employee);
        } else {
            logger.warn(Constants.EMPLOYEE_NOT_FOUND_LOG, id);
            return ResponseEntity.notFound().build();
        }
    }

    
    /*
     * USING FEIGN CLIENT
     * Handles HTTP requests and uses the service layer methods.
     * Includes logging for incoming requests and responses.
     */

    @Operation(summary = "Retrieve employees using Feign Client", description = "This API retrieves a list of employees using Feign Client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved employees",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error retrieving employees",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/feign")
    public ResponseEntity<String> getEmployeesUsingFeign() {
        logger.info(Constants.GET_EMPLOYEES_FEIGN_REQUEST_LOG);
        try {
            String employees = adminService.getEmployeesUsingFeign();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            logger.error(Constants.GET_EMPLOYEES_FEIGN_CLIENT_ERROR_LOG, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error retrieving employees");
        }
    }

    @Operation(summary = "Create an employee using Feign Client", description = "This API creates a new employee using Feign Client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminBO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error creating employee",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/feign/employees")
    public ResponseEntity<AdminBO> createEmployeeUsingFeign(@RequestBody AdminBO adminBO) {
        logger.info(Constants.CREATE_EMPLOYEE_FEIGN_REQUEST_LOG, adminBO);
        try {
            AdminBO createdEmployee = adminService.createEmployeeUsingFeign(adminBO);
            return ResponseEntity.status(201).body(createdEmployee);
        } catch (Exception e) {
            logger.error(Constants.SERVICE_ERROR_LOG, e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Retrieve an employee by ID using Feign Client", description = "This API retrieves an employee by their ID using Feign Client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved employee",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminBO.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error retrieving employee",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/feign/employees/{id}")
    public ResponseEntity<AdminBO> getEmployeeByIdUsingFeign(@Parameter(description = "ID of the employee to be retrieved", required = true)
                                                                         @PathVariable("id") Long id) {
        logger.info(Constants.GET_EMPLOYEE_BY_ID_FEIGN_REQUEST_LOG, id);
        try {
            AdminBO employee = adminService.getEmployeeByIdUsingFeign(id);
            if (employee != null) {
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error(Constants.ERROR_RETRIEVING_EMPLOYEE_BY_ID_FEIGN_LOG, id, e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
