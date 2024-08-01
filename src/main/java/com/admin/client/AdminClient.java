package com.admin.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.admin.bo.AdminBO;
import com.admin.entity.AdminEntity;

//@FeignClient(name = "employee-client", url = "${employee.api.url}")
@FeignClient(name = "employee-client", url = "${ems.provider.url}")
public interface AdminClient {
	
	Logger logger = LoggerFactory.getLogger(AdminClient.class);
	
	@GetMapping("/employees/display")
    String getEmployeesUsingFeign();

    @PostMapping("/employees/create")
    AdminEntity createEmployeeUsingFeign(@RequestBody AdminEntity adminEntity);

    @GetMapping("/employees/id/{id}")
    AdminEntity getEmployeeByIdUsingFeign(@PathVariable("id") Long id);
    
}
