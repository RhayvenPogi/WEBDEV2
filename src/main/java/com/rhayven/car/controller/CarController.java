package com.rhayven.car.controller;

import com.rhayven.car.service.CarService;
import com.rhayven.car.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cars", carService.getCars());
        return "index"; // Show all cars in a table
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("car", new Car());
        return "new"; // Form to add a new car
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute Car car) {
        carService.addCar(car);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable int id) {
        carService.deleteCar(id);
        return "redirect:/";
    }
}
