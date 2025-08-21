package com.rhayven.car.controller;

import com.rhayven.car.service.CarService;
import com.rhayven.car.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling web requests related to car management.
 */
@Controller
public class CarController {

    /** Service for managing car data */
    @Autowired
    private CarService carService;

    /**
     * Displays the homepage with a list of all cars.
     *
     * @param model the model to pass data to the view
     * @return the index view
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cars", carService.getCars());
        return "index"; // Show all cars in a table
    }

    /**
     * Shows the form to add a new car.
     *
     * @param model the model to pass data to the view
     * @return the new car form view
     */
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("car", new Car());
        return "new"; // Form to add a new car
    }

    /**
     * Handles the submission of the new car form.
     *
     * @param car the car to be added
     * @return redirection to the homepage
     */
    @PostMapping("/add")
    public String addCar(@ModelAttribute Car car) {
        carService.addCar(car);
        return "redirect:/";
    }


}
