package com.admin.controller;

import com.admin.bo.AdminBO;
import com.admin.service.AdminService;
import com.admin.service.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEmployeesUsingRestTemplate() {
        String mockResponse = "Mock Employee List";
        when(adminService.getEmployeesUsingRestTemplate()).thenReturn(mockResponse);

        String response = adminController.getEmployeesUsingRestTemplate();

        assertEquals(mockResponse, response);
        verify(adminService, times(1)).getEmployeesUsingRestTemplate();
    }

    @Test
    void testCreateEmployee() {
        AdminBO adminBO = new AdminBO(1L, "Amar Singh", "Software Eng", 50000L);
        when(adminService.createEmployeeUsingRestTemplate(any(AdminBO.class))).thenReturn(adminBO);

        ResponseEntity<AdminBO> response = adminController.createEmployee(adminBO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(adminBO, response.getBody());
        verify(adminService, times(1)).createEmployeeUsingRestTemplate(any(AdminBO.class));
    }

    @Test
    void testGetEmployeeById() {
        AdminBO adminBO = new AdminBO(1L, "Amar Singh", "Software Eng", 50000L);
        when(adminService.getEmployeeByIdUsingRestTemplate(anyLong())).thenReturn(adminBO);

        ResponseEntity<AdminBO> response = adminController.getEmployeeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adminBO, response.getBody());
        verify(adminService, times(1)).getEmployeeByIdUsingRestTemplate(anyLong());
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        when(adminService.getEmployeeByIdUsingRestTemplate(anyLong())).thenReturn(null);

        ResponseEntity<AdminBO> response = adminController.getEmployeeById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(adminService, times(1)).getEmployeeByIdUsingRestTemplate(anyLong());
    }

    // Feign client tests
    @Test
    void testGetEmployeesUsingFeign() {
        String mockResponse = "Mock Employee List Feign";
        when(adminService.getEmployeesUsingFeign()).thenReturn(mockResponse);

        ResponseEntity<String> response = adminController.getEmployeesUsingFeign();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(adminService, times(1)).getEmployeesUsingFeign();
    }

    @Test
    void testCreateEmployeeUsingFeign() {
        AdminBO adminBO = new AdminBO(1L, "Amar Singh", "Software Eng", 50000L);
        when(adminService.createEmployeeUsingFeign(any(AdminBO.class))).thenReturn(adminBO);

        ResponseEntity<AdminBO> response = adminController.createEmployeeUsingFeign(adminBO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(adminBO, response.getBody());
        verify(adminService, times(1)).createEmployeeUsingFeign(any(AdminBO.class));
    }

    @Test
    void testGetEmployeeByIdUsingFeign() {
        AdminBO adminBO = new AdminBO(1L, "Amar Singh", "Software Eng", 50000L);
        when(adminService.getEmployeeByIdUsingFeign(anyLong())).thenReturn(adminBO);

        ResponseEntity<AdminBO> response = adminController.getEmployeeByIdUsingFeign(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adminBO, response.getBody());
        verify(adminService, times(1)).getEmployeeByIdUsingFeign(anyLong());
    }

    @Test
    void testGetEmployeeByIdUsingFeignNotFound() {
        when(adminService.getEmployeeByIdUsingFeign(anyLong())).thenReturn(null);

        ResponseEntity<AdminBO> response = adminController.getEmployeeByIdUsingFeign(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(adminService, times(1)).getEmployeeByIdUsingFeign(anyLong());
    }
}