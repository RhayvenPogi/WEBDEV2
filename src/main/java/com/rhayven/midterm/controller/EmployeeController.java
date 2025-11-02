package com.rhayven.midterm.controller;

import com.rhayven.midterm.dto.EmployeeDTO;
import com.rhayven.midterm.entity.Employee;
import com.rhayven.midterm.exception.ResourceNotFoundException;
import com.rhayven.midterm.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class that handles employee-related web requests.
 * Provides CRUD operations such as listing, adding, editing, updating, and deleting employees.
 */
@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Constructor for injecting {@link EmployeeService}.
     *
     * @param employeeService the service for handling business logic
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Displays the homepage with the list of all employees.
     *
     * @param model the Spring {@link Model} object used to pass data to the view
     * @return the index view
     */
    @GetMapping("/")
    public String index(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "index";
    }

    /**
     * Displays the form for adding a new employee.
     *
     * @param model the Spring {@link Model} object used to pass data to the view
     * @return the new employee form view
     */
    @GetMapping("/add")
    public String addEmployee(Model model) {
        model.addAttribute("employeeDTO", new EmployeeDTO());
        return "new";
    }

    /**
     * Saves a new employee record after validating input data.
     *
     * @param employeeDTO the employee data transfer object containing form inputs
     * @param result      the {@link BindingResult} containing validation results
     * @return a redirect to the employee list view if successful, or back to the form if errors occur
     */
    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "new";
        }

        try {
            employeeService.saveEmployee(employeeDTO);
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.employeeDTO", e.getMessage());
            return "new";
        }

        return "redirect:/";
    }

    /**
     * Deletes an employee record by ID.
     *
     * @param id the ID of the employee to delete
     * @return a redirect to the employee list view
     */
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id) {
        try {
            employeeService.deleteEmployee(id);
        } catch (ResourceNotFoundException e) {
            // Optionally handle missing record gracefully
        }
        return "redirect:/";
    }

    /**
     * Displays the edit form for a specific employee.
     *
     * @param id    the ID of the employee to edit
     * @param model the Spring {@link Model} object used to pass data to the view
     * @return the edit employee form view
     * @throws ResourceNotFoundException if the employee with the given ID does not exist
     */
    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable int id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(employee.getEmail());

        model.addAttribute("employeeDTO", employeeDTO);
        model.addAttribute("id", id);
        return "edit";
    }

    /**
     * Updates an existing employee record after validating input data.
     *
     * @param id          the ID of the employee to update
     * @param employeeDTO the employee data transfer object containing updated inputs
     * @param result      the {@link BindingResult} containing validation results
     * @param model       the Spring {@link Model} object used to pass data to the view
     * @return a redirect to the employee list view if successful, or back to the edit form if errors occur
     * @throws ResourceNotFoundException if the employee with the given ID does not exist
     */
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable int id,
                                 @Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "edit";
        }

        try {
            employeeService.updateEmployee(id, employeeDTO);
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.employeeDTO", e.getMessage());
            model.addAttribute("id", id);
            return "edit";
        } catch (ResourceNotFoundException e) {
            throw e;
        }

        return "redirect:/";
    }
}
