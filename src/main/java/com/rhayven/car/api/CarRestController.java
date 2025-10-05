package com.rhayven.car.api;

import com.rhayven.car.dto.CarDTO;
import com.rhayven.car.entity.Car;
import com.rhayven.car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarRestController {
    private final CarService carService;

    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public List<Car> getCars(){
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable int id) {
        return carService.getCarById(id);
    }

    @PostMapping("/cars")
    public void createCar(@Valid @RequestBody CarDTO carDTO) {
        carService.save(carDTO);
    }

    @PutMapping("/cars/{id}")
    public void updateCar(@PathVariable int id, @Valid @RequestBody CarDTO carDTO) {
        carService.update(id, carDTO);
    }

    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable int id) {
        carService.delete(id);
    }
}
