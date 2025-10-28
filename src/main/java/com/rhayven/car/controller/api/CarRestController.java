package com.rhayven.car.controller.api;

import com.rhayven.car.dto.CarDTO;
import com.rhayven.car.entity.Car;
import com.rhayven.car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins= "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/cars")
public class CarRestController {
    private final CarService carService;

    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getCars(){
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable int id) {
        Car car = carService.getCarById(id);
        if (car == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID " + id + " not found");
        }
        return car;
    }

    @PostMapping
    public Car createCar(@Valid @RequestBody CarDTO car) {

        return carService.save(car);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable int id, @Valid @RequestBody CarDTO car) {
        Car updateCar = carService.getCarById(id);
        if(updateCar == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID " + id + " not found");
        }
        return carService.update(id, car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable int id) {
        if(carService.getCarById(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID " + id + " not found");
        }
        carService.delete(id);
    }
}
