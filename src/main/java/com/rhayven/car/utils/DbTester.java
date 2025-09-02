package com.rhayven.car.utils;

import com.rhayven.car.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbTester implements CommandLineRunner {
    private final CarRepository carRepository;

    public DbTester(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        //Car car = new Car();

    }
}
