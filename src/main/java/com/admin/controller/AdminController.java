package com.admin.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.admin.service.AdminService;
import com.admin.util.Constants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/client")
@Validated
public class AdminController {
	
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
    private AdminService adminService;

    @GetMapping("/rest")
    public String getEmployeesUsingRestTemplate() {
        logger.info(Constants.GET_EMPLOYEES_REST_LOG);
        return adminService.getEmployeesUsingRestTemplate();
    }
	
    @PostMapping("rest/employees")
    public ResponseEntity<AdminBO> createEmployee(@RequestBody @Valid AdminBO adminBO) {
        logger.info(Constants.CREATE_EMPLOYEE_REQUEST_LOG, adminBO);
        AdminBO createdEmployee = adminService.createEmployeeUsingRestTemplate(adminBO);
        logger.info(Constants.CREATE_EMPLOYEE_RESPONSE_LOG, createdEmployee);
        return ResponseEntity.status(201).body(createdEmployee);
    }

    @GetMapping("rest/employees/{id}")
    public ResponseEntity<AdminBO> getEmployeeById(
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
//	@GetMapping("/rest")
//	public List<AdminEntity> showEmp() {
//        List<AdminEntity> emp = adminService.getAllEmp();
//        if (emp == null) {
//            emp = new ArrayList<>();
//        }
//        return emp; // Spring will automatically convert this list to JSON
//    }


//    @GetMapping("/feign")
//    public String getEmployeesUsingFeign() {
//        logger.info("GET /client/feign");
//        return adminService.getEmployeesUsingFeign();
//    }
//    
    
    /*
     * USING FEIGN CLIENT
     * Handles HTTP requests and uses the service layer methods.
     * Includes logging for incoming requests and responses.
     */

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

    @PostMapping("/feign/employees")
    public ResponseEntity<AdminBO> createEmployeeUsingFeign(@RequestBody AdminBO adminBO) {
        logger.info(Constants.CREATE_EMPLOYEE_FEIGN_REQUEST_LOG, adminBO);
        try {
            AdminBO createdEmployee = adminService.createEmployee(adminBO);
            return ResponseEntity.status(201).body(createdEmployee);
        } catch (Exception e) {
            logger.error(Constants.SERVICE_ERROR_LOG, e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/feign/employees/{id}")
    public ResponseEntity<AdminBO> getEmployeeByIdUsingFeign(@PathVariable("id") Long id) {
        logger.info(Constants.GET_EMPLOYEE_BY_ID_FEIGN_REQUEST_LOG, id);
        try {
            AdminBO employee = adminService.getEmployeeById(id);
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
