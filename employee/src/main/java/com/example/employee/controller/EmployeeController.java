package com.example.employee.controller;

import com.example.employee.dto.EmployeeRequestDTO;
import com.example.employee.dto.EmployeeResponseDTO;
import com.example.employee.services.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Create a new employee.
     * Endpoint: POST /api/employees
     */
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO request) {
        log.info("Received request to create employee: {}", request.getEmail());
        EmployeeResponseDTO created = employeeService.createEmployee(request);
        return ResponseEntity.created(URI.create("/api/employees/" + created.getId()))
                             .body(created);
    }

    /**
     * Get employee by ID.
     * Endpoint: GET /api/employees/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        log.info("Fetching employee with ID: {}", id);
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    /**
     * Get all employees (excluding soft-deleted).
     * Endpoint: GET /api/employees
     */
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        log.info("Fetching all employees");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /**
     * Update employee details by ID.
     * Endpoint: PUT /api/employees/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO request) {
        log.info("Updating employee with ID: {}", id);
        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }

    /**
     * Soft delete employee (mark as deleted).
     * Endpoint: DELETE /api/employees/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("Deleting employee with ID: {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search employees by name (case-insensitive).
     * Endpoint: GET /api/employees/search?name={name}
     */
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponseDTO>> searchEmployeesByName(@RequestParam String name) {
        log.info("Searching employees with name containing: {}", name);
        return ResponseEntity.ok(employeeService.searchEmployeesByName(name));
    }
}
