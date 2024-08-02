package com.admin.service;

import com.admin.bo.AdminBO;

public interface AdminService {
	
	//REST TEMPLATE
	String getEmployeesUsingRestTemplate();
	AdminBO createEmployeeUsingRestTemplate(AdminBO adminBO);
	AdminBO getEmployeeByIdUsingRestTemplate(Long id);
	
	//FEIGN CLIENT
	String getEmployeesUsingFeign();
	AdminBO createEmployeeUsingFeign(AdminBO adminBO);
	AdminBO getEmployeeByIdUsingFeign(Long id);

}
