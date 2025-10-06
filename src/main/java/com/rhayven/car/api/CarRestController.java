package com.rhayven.car.api;

import com.rhayven.car.dto.CarDTO;
import com.rhayven.car.entity.Car;
import com.rhayven.car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public Car createCar(@Valid @RequestBody CarDTO carDTO) {

        return carService.save(carDTO);
    }

    @PutMapping("/cars/{id}")
    public Car updateCar(@PathVariable int id, @Valid @RequestBody CarDTO carDTO) {
        Car updateCar = carService.getCarById(id);
        if(updateCar == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID " + id + " not found");
        }
        return carService.update(id, carDTO);
    }

    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable int id) {
        if(carService.getCarById(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID " + id + " not found");
        }
        carService.delete(id);
    }
}
