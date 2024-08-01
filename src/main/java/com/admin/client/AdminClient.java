package com.admin.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.admin.bo.AdminBO;

@FeignClient(name = "employee-client", url = "${employee.api.url}")
public interface AdminClient {
	
	Logger logger = LoggerFactory.getLogger(AdminClient.class);
	

    @GetMapping("/employees/display")
    String getEmployeesUsingFeign();
    
    @PostMapping("/employees/create")
    AdminBO createEmployeeUsingFeign(@RequestBody AdminBO adminBO);
    
    @GetMapping("/employees/id/{id}")
    AdminBO getEmployeeByIdUsingFeign(@PathVariable("id") Long id);


    
}
