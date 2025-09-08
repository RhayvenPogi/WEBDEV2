package com.rhayven.car.service;

import com.rhayven.car.dto.CarDTO;
import com.rhayven.car.entity.Car;
import com.rhayven.car.exception.ResourceNotFoundException;
import com.rhayven.car.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void save(CarDTO carDTO) {
        Car car = new Car();
        car.setLicensePlateNumber(carDTO.getLicensePlateNumber());
        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setColor(carDTO.getColor());
        car.setBodyType(carDTO.getBodyType());
        car.setEngineType(carDTO.getEngineType());
        car.setTransmission(carDTO.getTransmission());

        carRepository.save(car);
    }

    public Car getCarById(int id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));
    }

    public void update(int id, CarDTO carDTO) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));

        car.setLicensePlateNumber(carDTO.getLicensePlateNumber());
        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setColor(carDTO.getColor());
        car.setBodyType(carDTO.getBodyType());
        car.setEngineType(carDTO.getEngineType());
        car.setTransmission(carDTO.getTransmission());

        carRepository.save(car);
    }

    public void delete(int id) {
        carRepository.deleteById(id);
    }
}

