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

    public void updateCar(int id, Car update) {
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getCarId() == id) {
                carList.set(i, update);
                writeToDisk();
                break;
            }
        }
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
        if (!file.exists()) {
            System.out.println("File not found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Ensure we have all 9 fields
                if (data.length < 9) continue;

                Car car = new Car();
                car.setCarId(Integer.parseInt(data[0]));
                car.setLicensePlateNumber(data[1]);
                car.setMake(data[2]);
                car.setModel(data[3]);
                car.setYear(Integer.parseInt(data[4]));
                car.setColor(data[5]);
                car.setBodyType(data[6]);
                car.setEngineType(data[7]);
                car.setTransmission(data[8]);

                carList.add(car);
            }

        } catch (IOException e) {
            System.out.println("Oh no! Error: " + e.getMessage());
        }
    }

    private void writeToDisk() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Car car : carList) {
                bw.write(car.getCarId() + "," +
                        car.getLicensePlateNumber() + "," +
                        car.getMake() + "," +
                        car.getModel() + "," +
                        car.getYear() + "," +
                        car.getColor() + "," +
                        car.getBodyType() + "," +
                        car.getEngineType() + "," +
                        car.getTransmission());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Oh no! Error: " + e.getMessage());
        }
    }
}
