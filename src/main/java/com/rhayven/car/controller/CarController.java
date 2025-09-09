package com.rhayven.car.controller;

import com.rhayven.car.dto.CarDTO;
import com.rhayven.car.entity.Car;
import com.rhayven.car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public String index(Model model) {
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "index";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("carDTO", new CarDTO());
        return "new";
    }

    @PostMapping("/save")
    public String saveCar(@Valid @ModelAttribute("carDTO") CarDTO carDTO,
                          BindingResult result) {
        if (result.hasErrors()) {
            return "new";
        }

        carService.save(carDTO);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable int id) {
        carService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editCar(@PathVariable int id, Model model) {
        Car car = carService.getCarById(id);
        CarDTO carDTO = new CarDTO();
        carDTO.setLicensePlateNumber(car.getLicensePlateNumber());
        carDTO.setMake(car.getMake());
        carDTO.setModel(car.getModel());
        carDTO.setYear(car.getYear());
        carDTO.setColor(car.getColor());
        carDTO.setBodyType(car.getBodyType());
        carDTO.setEngineType(car.getEngineType());
        carDTO.setTransmission(car.getTransmission());

        model.addAttribute("carDTO", carDTO);
        model.addAttribute("carId", id);
        return "edit";

    }

    @PostMapping("/update/{id}")
    public String storeUpdateCar(@PathVariable int id,
                                 @Valid @ModelAttribute("carDTO") CarDTO carDTO,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("carId", id);
            return "edit";
        }

        carService.update(id, carDTO);
        return "redirect:/";
    }
}
