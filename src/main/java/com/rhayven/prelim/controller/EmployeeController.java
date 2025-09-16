package com.rhayven.prelim.controller;

import com.rhayven.prelim.dto.EmployeeDTO;
import com.rhayven.prelim.entity.Employee;
import com.rhayven.prelim.repository.EmployeeRepo;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class EmployeeController {
    private final EmployeeRepo employeeRepo;

    public EmployeeController(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @GetMapping("/")
    public String index(Model model){
        List<Employee> employee= employeeRepo.findAll();
        model.addAttribute("employee",employee);
        return "index";
    }

    @GetMapping("/add")
    public String addEmployee(Model model){
        model.addAttribute("employee", new EmployeeDTO());
        return "new";
    }

    @PostMapping("/save")
    public String saveCar(@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
                          BindingResult result) {
        if (result.hasErrors()) {
            return "new";
        }

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());

        employeeRepo.save(employee);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable int id) {
        employeeRepo.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable int id, Model model) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(employee.getEmail());

        model.addAttribute("employeeDTO", employeeDTO);
        model.addAttribute("id", id);
        return "edit";

    }

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
                .orElseThrow();

        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());

        employeeRepo.save(employee);

        return "redirect:/";
    }




}






