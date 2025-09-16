package com.rhayven.prelim.controller;

import com.rhayven.prelim.dto.EmployeeDTO;
import com.rhayven.prelim.entity.Employee;
import com.rhayven.prelim.exception.ResourceNotFoundException;
import com.rhayven.prelim.repository.EmployeeRepo;
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

    private final EmployeeRepo employeeRepo;

    /**
     * Constructor for injecting {@link EmployeeRepo}.
     *
     * @param employeeRepo the repository for employee data access
     */
    public EmployeeController(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    /**
     * Displays the homepage with the list of all employees.
     *
     * @param model the Spring {@link Model} object used to pass data to the view
     * @return the index view
     */
    @GetMapping("/")
    public String index(Model model) {
        List<Employee> employees = employeeRepo.findAll();
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
    public String saveCar(@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
                          BindingResult result) {
        if (result.hasErrors()) {
            return "new";
        }

        if (employeeRepo.findByEmail(employeeDTO.getEmail()).isPresent()) {
            result.rejectValue("email", "error.employeeDTO",
                    "This email is already registered. Please use a different email.");
            return "new";
        }

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());

        employeeRepo.save(employee);

        return "redirect:/";
    }

    /**
     * Deletes an employee record by ID.
     *
     * @param id the ID of the employee to delete
     * @return a redirect to the employee list view
     */
    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable int id) {
        employeeRepo.deleteById(id);
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
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));

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
    public String storeUpdateCar(@PathVariable int id,
                                 @Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "edit";
        }

        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));

        // Check if email is already used by another employee
        employeeRepo.findByEmail(employeeDTO.getEmail()).ifPresent(e -> {
            if (e.getId() != id) {
                result.rejectValue("email", "error.employeeDTO",
                        "This email is already in use by another employee. Please enter a different email.");
            }
        });

        // If duplicate email was found, return to edit form
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "edit";
        }

        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());

        employeeRepo.save(employee);

        return "redirect:/";
    }
}
