package com.example.employee.services;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.employee.dto.EmployeeRequestDTO;
import com.example.employee.dto.EmployeeResponseDTO;
import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * Create a new employee and save to DB.
     * Validates duplicate email before saving.
     */
	    @Override
	    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO request) {
	        log.info("Creating new employee with email: {}", request.getEmail());
	
	        employeeRepository.findByEmail(request.getEmail()).ifPresent(emp -> {
	            throw new IllegalArgumentException("Employee with email already exists: " + request.getEmail());
	        });
	
	        Employee employee = new Employee();
	        employee.setName(request.getName());
	        employee.setEmail(request.getEmail());
	        employee.setPosition(request.getPosition());
	
	        Employee saved = employeeRepository.save(employee);
	        return mapToResponse(saved);
	    }

    /**
     * Find employee by ID.
     */
    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .filter(emp -> !emp.getIsDeleted())
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + id));

        return mapToResponse(employee);
    }

    /**
     * Get all employees excluding deleted ones.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findByIsDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update employee details by ID.
     */
    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO request) {
        log.info("Updating employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .filter(emp -> !emp.getIsDeleted())
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + id));

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPosition(request.getPosition());

        Employee updated = employeeRepository.save(employee);
        return mapToResponse(updated);
    }

    /**
     * Soft delete employee by ID (mark as deleted instead of removing).
     */
    @Override
    public void deleteEmployee(Long id) {
        log.info("Soft deleting employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .filter(emp -> !emp.getIsDeleted())
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + id));

        employee.setIsDeleted(true);
        employeeRepository.save(employee);
    }

    /**
     * Search employees by name (case-insensitive).
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> searchEmployeesByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .filter(emp -> !emp.getIsDeleted())
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Utility method to map Entity → ResponseDTO.
     */
    private EmployeeResponseDTO mapToResponse(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPosition(employee.getPosition());
        dto.setCreatedAt(employee.getCreatedAt());
        dto.setUpdatedAt(employee.getUpdatedAt());
        return dto;
    }
}
