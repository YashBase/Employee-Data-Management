package com.example.employee.services;

import com.example.employee.dto.EmployeeRequestDTO;
import com.example.employee.dto.EmployeeResponseDTO;
import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmployeeServiceImplTest {

    private EmployeeRepository employeeRepository;
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        // Create mock manually
        employeeRepository = Mockito.mock(EmployeeRepository.class);

        // Inject mock into service
        employeeService = new EmployeeServiceImpl(employeeRepository);

        employee = Employee.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .position("Developer")
                .isDeleted(false)
                .build();

        requestDTO = EmployeeRequestDTO.builder()
                .name("John Doe")
                .email("john@example.com")
                .position("Developer")
                .build();
    }

    @Test
    @DisplayName("Create employee successfully")
    void testCreateEmployee_success() {
        Mockito.when(employeeRepository.findByEmail(requestDTO.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenReturn(employee);

        EmployeeResponseDTO response = employeeService.createEmployee(requestDTO);

        assertThat(response.getName()).isEqualTo("John Doe");
        Mockito.verify(employeeRepository).save(Mockito.any(Employee.class));
    }

    @Test
    @DisplayName("Create employee with existing email throws exception")
    void testCreateEmployee_duplicateEmail() {
        Mockito.when(employeeRepository.findByEmail(requestDTO.getEmail()))
                .thenReturn(Optional.of(employee));

        assertThatThrownBy(() -> employeeService.createEmployee(requestDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Employee with email already exists");
    }

    @Test
    @DisplayName("Get employee by ID successfully")
    void testGetEmployeeById_success() {
        Mockito.when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        EmployeeResponseDTO response = employeeService.getEmployeeById(1L);

        assertThat(response.getName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Get employee by ID not found throws exception")
    void testGetEmployeeById_notFound() {
        Mockito.when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Employee not found");
    }

    @Test
    @DisplayName("Update employee successfully")
    void testUpdateEmployee_success() {
        Mockito.when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenReturn(employee);

        EmployeeRequestDTO updateRequest = EmployeeRequestDTO.builder()
                .name("Jane Doe")
                .email("jane@example.com")
                .position("Tester")
                .build();

        EmployeeResponseDTO response = employeeService.updateEmployee(1L, updateRequest);

        assertThat(response.getName()).isEqualTo("Jane Doe");
        assertThat(response.getEmail()).isEqualTo("jane@example.com");
    }

    @Test
    @DisplayName("Delete employee successfully (soft delete)")
    void testDeleteEmployee_success() {
        Mockito.when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        assertThat(employee.getIsDeleted()).isTrue();
        Mockito.verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Search employees by name")
    void testSearchEmployeesByName() {
        Employee emp1 = Employee.builder()
                .id(1L).name("Alice").email("alice@example.com")
                .position("Dev").isDeleted(false).build();

        Employee emp2 = Employee.builder()
                .id(2L).name("Alicia").email("alicia@example.com")
                .position("Dev").isDeleted(false).build();

        Mockito.when(employeeRepository.findByNameContainingIgnoreCase("ali"))
                .thenReturn(Arrays.asList(emp1, emp2));

        List<EmployeeResponseDTO> results = employeeService.searchEmployeesByName("ali");

        assertThat(results.size()).isEqualTo(2);
        assertThat(results.get(0).getName().toLowerCase()).contains("ali");
    }

    @Test
    @DisplayName("Get all employees excluding deleted ones")
    void testGetAllEmployees() {
        Employee emp1 = Employee.builder()
                .id(1L).name("Alice").email("alice@example.com")
                .position("Dev").isDeleted(false).build();

        Employee emp2 = Employee.builder()
                .id(2L).name("Bob").email("bob@example.com")
                .position("Dev").isDeleted(true).build();

        Mockito.when(employeeRepository.findByIsDeletedFalse())
                .thenReturn(List.of(emp1));

        List<EmployeeResponseDTO> results = employeeService.getAllEmployees();

        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("Alice");
    }
}
