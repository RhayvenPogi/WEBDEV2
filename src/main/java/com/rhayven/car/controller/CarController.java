package com.rhayven.car.controller;

import com.rhayven.car.exception.ResourceNotFoundException;
import com.rhayven.car.repository.CarRepository;
import com.rhayven.car.model.Car;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class CarController {

    CarRepository carRepository;

    public CarController(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars",carRepository.findAll());
        cars.forEach(car -> {
            System.out.println(car.getMake());
        });
        return "index"; // Show all cars in a table
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        Car car = new Car();
        model.addAttribute("car",car);
        return "new"; // Form to add a new car
    }

    @PostMapping("/save")
    public String saveCar(@ModelAttribute Car car) {
        carRepository.save(car);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable int id) {
        carRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editCar(@PathVariable int id, Model model){
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));
        model.addAttribute("car", car);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String storeUpdateCar(@PathVariable int id, @ModelAttribute Car car){
        car.setCarId(id);
        carRepository.save(car);
        return "redirect:/";
    }
}
