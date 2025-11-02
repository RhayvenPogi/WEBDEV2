package com.rhayven.midterm.controller.api;

import com.rhayven.midterm.dto.EmployeeDTO;
import com.rhayven.midterm.entity.Employee;
import com.rhayven.midterm.exception.ResourceNotFoundException;
import com.rhayven.midterm.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing employees via JSON-based API endpoints.
 * Provides CRUD operations using HTTP methods (GET, POST, PUT, DELETE).
 */
@CrossOrigin
@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    /**
     * Constructor for dependency injection.
     *
     * @param employeeService the service for handling employee operations
     */
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Retrieve all employees.
     *
     * @return list of employees
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * Retrieve a specific employee by ID.
     *
     * @param id the ID of the employee
     * @return the employee if found
     * @throws ResourceNotFoundException if employee not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    /**
     * Create a new employee.
     *
     * @param employeeDTO the employee data transfer object
     * @return the created employee
     */
    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            employeeService.saveEmployee(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Update an existing employee.
     *
     * @param id          the ID of the employee to update
     * @param employeeDTO the updated employee data
     * @return a response message
     * @throws ResourceNotFoundException if employee not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            employeeService.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok("Employee updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Delete an employee by ID.
     *
     * @param id the ID of the employee to delete
     * @return a success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
