package com.admin.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.admin.entity.AdminEntity;

@Component
public class AdminBO {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminBO.class);
	
	public String processEmployees(String emp) {
		logger.info("Processing employee data: {}", emp);
		return emp;
	}

}
