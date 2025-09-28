package com.example.employee.repositoy;



import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Save an employee successfully")
    void testSaveEmployee() {
        Employee employee = Employee.builder()
                .name("John Doe")
                .email("john@example.com")
                .position("Developer")
                .isDeleted(false)
                .build();

        Employee saved = employeeRepository.save(employee);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Find employee by email")
    void testFindByEmail() {
        Employee employee = Employee.builder()
                .name("Alice")
                .email("alice@example.com")
                .position("Tester")
                .isDeleted(false)
                .build();

        employeeRepository.save(employee);

        Optional<Employee> found = employeeRepository.findByEmail("alice@example.com");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Alice");
    }

    @Test
    @DisplayName("Find employees by name containing")
    void testFindByNameContainingIgnoreCase() {
        employeeRepository.save(Employee.builder()
                .name("Bob Smith")
                .email("bob@example.com")
                .position("DevOps")
                .isDeleted(false)
                .build());

        employeeRepository.save(Employee.builder()
                .name("Bobby Brown")
                .email("bobby@example.com")
                .position("Designer")
                .isDeleted(false)
                .build());

        List<Employee> results = employeeRepository.findByNameContainingIgnoreCase("bob");
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Find all employees not deleted")
    void testFindByIsDeletedFalse() {
        employeeRepository.save(Employee.builder()
                .name("Carol")
                .email("carol@example.com")
                .position("Manager")
                .isDeleted(false)
                .build());

        employeeRepository.save(Employee.builder()
                .name("Dave")
                .email("dave@example.com")
                .position("Analyst")
                .isDeleted(true)
                .build());

        List<Employee> results = employeeRepository.findByIsDeletedFalse();
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("Carol");
    }
}
