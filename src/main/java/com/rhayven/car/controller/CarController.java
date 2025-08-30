package com.rhayven.car.controller;

import com.rhayven.car.service.CarService;
import com.rhayven.car.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.AttributedString;

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

    @PostMapping("/save")
    public String saveCar(@ModelAttribute Car car) {
        carService.addCar(car);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteCar(@RequestParam("id") int id) {
        carService.deleteCar(id);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editCar(@RequestParam("id") int id, Model model){
        Car car = carService.getCar(id);
        if (car != null) {
            model.addAttribute("car", car);
            return "edit";
        }

        return "redirect:/";
    }

    @PostMapping("/update")
    public String storeUpdateCar(@ModelAttribute("car") Car car){
        if (car.getCarId() > 0) {
            Car existingCar = carService.getCar(car.getCarId());
            if (existingCar != null) {
                // Perform the update
                carService.updateCar(car.getCarId(), car);
            }
        }

        return "redirect:/";

    }
}
