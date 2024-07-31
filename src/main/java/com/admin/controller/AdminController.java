package com.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.entity.AdminEntity;
import com.admin.service.AdminService;

@RestController
@RequestMapping("/client")
public class AdminController {
	
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
    private AdminService adminService;

    @GetMapping("/rest")
    public String getEmployeesUsingRestTemplate() {
        logger.info("GET /client/rest");
        return adminService.getEmployeesUsingRestTemplate();
    }
	
	
//	@GetMapping("/rest")
//	public List<AdminEntity> showEmp() {
//        List<AdminEntity> emp = adminService.getAllEmp();
//        if (emp == null) {
//            emp = new ArrayList<>();
//        }
//        return emp; // Spring will automatically convert this list to JSON
//    }


    @GetMapping("/feign")
    public String getEmployeesUsingFeign() {
        logger.info("GET /client/feign");
        return adminService.getEmployeesUsingFeign();
    }

}
