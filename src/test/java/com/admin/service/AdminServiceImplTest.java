package com.admin.service;

import com.admin.AdminMapper;
import com.admin.bo.AdminBO;
import com.admin.client.AdminClient;
import com.admin.entity.AdminEntity;
import com.admin.helper.RestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminServiceImplTest {

    @Mock
    private RestHelper restHelper;

    @Mock
    private AdminClient adminClient;

    @Mock
    private AdminMapper adminMapper;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests for RestTemplate methods

    @Test
    void testGetEmployeesUsingRestTemplate() {
        String mockResponse = "Mock Employee List";
        when(restHelper.getEmployees()).thenReturn(mockResponse);

        String response = adminService.getEmployeesUsingRestTemplate();

        assertEquals(mockResponse, response);
        verify(restHelper, times(1)).getEmployees();
    }

    @Test
    void testCreateEmployeeUsingRestTemplate() {
        AdminBO adminBO = new AdminBO(1L, "Jane Doe", "Manager", 60000L);
        AdminEntity adminEntity = new AdminEntity(1L, "Jane Doe", "Manager", 60000L);
        when(adminMapper.toEntity(any(AdminBO.class))).thenReturn(adminEntity);
        when(restHelper.createEmployee(any(AdminEntity.class))).thenReturn(adminEntity);
        when(adminMapper.toBO(any(AdminEntity.class))).thenReturn(adminBO);

        AdminBO createdAdminBO = adminService.createEmployeeUsingRestTemplate(adminBO);

        assertEquals(adminBO, createdAdminBO);
        verify(adminMapper, times(1)).toEntity(any(AdminBO.class));
        verify(restHelper, times(1)).createEmployee(any(AdminEntity.class));
        verify(adminMapper, times(1)).toBO(any(AdminEntity.class));
    }

    @Test
    void testGetEmployeeByIdUsingRestTemplate() {
        AdminBO adminBO = new AdminBO(1L, "Jane Doe", "Manager", 60000L);
        AdminEntity adminEntity = new AdminEntity(1L, "Jane Doe", "Manager", 60000L);
        when(restHelper.getEmployeeById(anyLong())).thenReturn(adminEntity);
        when(adminMapper.toBO(any(AdminEntity.class))).thenReturn(adminBO);

        AdminBO response = adminService.getEmployeeByIdUsingRestTemplate(1L);

        assertEquals(adminBO, response);
        verify(restHelper, times(1)).getEmployeeById(anyLong());
        verify(adminMapper, times(1)).toBO(any(AdminEntity.class));
    }

    // Tests for Feign client methods

    @Test
    void testGetEmployeesUsingFeign() {
        String mockResponse = "Mock Employee List Feign";
        when(adminClient.getEmployeesUsingFeign()).thenReturn(mockResponse);

        String response = adminService.getEmployeesUsingFeign();

        assertEquals(mockResponse, response);
        verify(adminClient, times(1)).getEmployeesUsingFeign();
    }

    @Test
    void testCreateEmployeeUsingFeign() {
        AdminBO adminBO = new AdminBO(1L, "Jane Doe", "Manager", 60000L);
        AdminEntity adminEntity = new AdminEntity(1L, "Jane Doe", "Manager", 60000L);
        when(adminMapper.toEntity(any(AdminBO.class))).thenReturn(adminEntity);
        when(adminClient.createEmployeeUsingFeign(any(AdminEntity.class))).thenReturn(adminEntity);
        when(adminMapper.toBO(any(AdminEntity.class))).thenReturn(adminBO);

        AdminBO createdAdminBO = adminService.createEmployeeUsingFeign(adminBO);

        assertEquals(adminBO, createdAdminBO);
        verify(adminMapper, times(1)).toEntity(any(AdminBO.class));
        verify(adminClient, times(1)).createEmployeeUsingFeign(any(AdminEntity.class));
        verify(adminMapper, times(1)).toBO(any(AdminEntity.class));
    }

    @Test
    void testGetEmployeeByIdUsingFeign() {
        AdminBO adminBO = new AdminBO(1L, "Jane Doe", "Manager", 60000L);
        AdminEntity adminEntity = new AdminEntity(1L, "Jane Doe", "Manager", 60000L);
        when(adminClient.getEmployeeByIdUsingFeign(anyLong())).thenReturn(adminEntity);
        when(adminMapper.toBO(any(AdminEntity.class))).thenReturn(adminBO);

        AdminBO response = adminService.getEmployeeByIdUsingFeign(1L);

        assertEquals(adminBO, response);
        verify(adminClient, times(1)).getEmployeeByIdUsingFeign(anyLong());
        verify(adminMapper, times(1)).toBO(any(AdminEntity.class));
    }

}
