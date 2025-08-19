package com.rhayven.car.service;

import com.rhayven.car.model.Car;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

/**
 * Service class for managing car records.
 * Provides basic CRUD operations and file persistence using CSV.
 */
@Service
public class CarService {
    private ArrayList<Car> carList;
    private final String FILE_NAME = "cars_database.csv";

    public CarService() {
        carList = new ArrayList<>();
        readFromDisk();
    }

    public ArrayList<Car> getCars() {
        return carList;
    }

    public void addCar(Car car) {
        car.setCarId(getLastId() + 1);
        carList.add(car);
        writeToDisk();
    }

    public void deleteCar(int id) {
        carList.removeIf(car -> car.getCarId() == id);
        // Reassign IDs sequentially after deletion
        for (int i = 0; i < carList.size(); i++) {
            carList.get(i).setCarId(i + 1);
        }
        writeToDisk();
    }

    public Car getCar(int id) {
        for (Car car : carList) {
            if (car.getCarId() == id)
                return car;
        }
        return null;
    }

    public int getLastId() {
        if (carList.isEmpty()) {
            return 0;
        }
        return carList.get(carList.size() - 1).getCarId();
    }

    private void readFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            carList.clear();

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",", -1);
                if (tokens.length == 9) {
                    Car car = new Car(
                            Integer.parseInt(tokens[0]),
                            tokens[1],
                            tokens[2],
                            tokens[3],
                            Integer.parseInt(tokens[4]),
                            tokens[5],
                            tokens[6],
                            tokens[7],
                            tokens[8]
                    );
                    carList.add(car);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToDisk() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Car car : carList) {
                String line = car.getCarId() + "," +
                        car.getLicensePlateNumber() + "," +
                        car.getMake() + "," +
                        car.getModel() + "," +
                        car.getYear() + "," +
                        car.getColor() + "," +
                        car.getBodyType() + "," +
                        car.getEngineType() + "," +
                        car.getTransmission();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
