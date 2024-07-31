package com.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmployeeManagementSystemAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemAdminApplication.class, args);
	}

}
