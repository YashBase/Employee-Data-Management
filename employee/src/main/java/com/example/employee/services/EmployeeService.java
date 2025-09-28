	package com.example.employee.services;
	
	
	
	import java.util.List;
	
	import org.springframework.stereotype.Service;
	
	import com.example.employee.dto.EmployeeRequestDTO;
	import com.example.employee.dto.EmployeeResponseDTO;
	
	@Service
	public interface EmployeeService {
	
	    // Create a new employee
	    EmployeeResponseDTO createEmployee(EmployeeRequestDTO request);
	
	    // Get employee by ID
	    EmployeeResponseDTO getEmployeeById(Long id);
	
	    // Get all employees (excluding soft-deleted ones)
	    List<EmployeeResponseDTO> getAllEmployees();
	
	    // Update employee details
	    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO request);
	
	    // Soft delete (mark as deleted)
	    void deleteEmployee(Long id);
	
	    // Search employees by name (case-insensitive)
	    List<EmployeeResponseDTO> searchEmployeesByName(String name);
	}