package com.admin.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "employee-client", url = "${employee.api.url}")
public interface AdminClient {
	
	Logger logger = LoggerFactory.getLogger(AdminClient.class);

    @GetMapping("/employees/display")
    String getEmployees();

}
