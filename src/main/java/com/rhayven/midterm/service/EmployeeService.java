package com.rhayven.midterm.service;

import com.rhayven.midterm.dto.EmployeeDTO;
import com.rhayven.midterm.entity.Employee;
import com.rhayven.midterm.exception.ResourceNotFoundException;
import com.rhayven.midterm.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling employee-related business logic.
 * This class manages CRUD operations and ensures clean separation
 * between the controller and data access layers.
 */
@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    /**
     * Constructor-based dependency injection for {@link EmployeeRepo}.
     *
     * @param employeeRepo the repository for employee data access
     */
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    /**
     * Retrieves all employees from the database.
     *
     * @return a list of {@link Employee} objects
     */
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    /**
     * Saves a new employee to the database.
     *
     * @param employeeDTO the employee data transfer object containing input data
     * @throws IllegalArgumentException if the email is already registered
     */
    public void saveEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepo.findByEmail(employeeDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("This email is already registered. Please use a different email.");
        }

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employeeRepo.save(employee);
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param id the ID of the employee to delete
     * @throws ResourceNotFoundException if employee not found
     */
    public void deleteEmployee(int id) {
        if (!employeeRepo.existsById(id)) {
            throw new ResourceNotFoundException("Employee", id);
        }
        employeeRepo.deleteById(id);
    }

    /**
     * Finds an employee by their ID.
     *
     * @param id the ID of the employee
     * @return the found {@link Employee} object
     * @throws ResourceNotFoundException if no employee exists with the given ID
     */
    public Employee getEmployeeById(int id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }

    /**
     * Updates an existing employee with new data.
     *
     * @param id          the ID of the employee to update
     * @param employeeDTO the updated employee data
     * @throws ResourceNotFoundException if the employee does not exist
     * @throws IllegalArgumentException  if the email is already used by another employee
     */
    public void updateEmployee(int id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));

        // Check if email is already used by another employee
        employeeRepo.findByEmail(employeeDTO.getEmail()).ifPresent(existing -> {
            if (existing.getId() != id) {
                throw new IllegalArgumentException("This email is already in use by another employee.");
            }
        });

        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employeeRepo.save(employee);
    }
}
