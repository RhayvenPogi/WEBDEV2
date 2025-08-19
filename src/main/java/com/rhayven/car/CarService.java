package com.rhayven.car;

import com.rhayven.car.Car;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing car records.
 * Provides CRUD operations, search functionality, and file persistence using CSV.
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

    public void updateCar(int id, Car update) {
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getCarId() == id) {
                update.setCarId(id); // keep original id
                carList.set(i, update);
                writeToDisk();
                break;
            }
        }
    }

    public List<Car> searchCar(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return carList;
        }
        String lowerKeyword = keyword.toLowerCase();

        List<Car> result = new ArrayList<>();
        for (Car car : carList) {
            if (String.valueOf(car.getCarId()).equals(keyword)
                    || String.valueOf(car.getLicensePlateNumber()).contains(keyword)
                    || car.getMake().toLowerCase().contains(lowerKeyword)
                    || car.getModel().toLowerCase().contains(lowerKeyword)
                    || String.valueOf(car.getYear()).contains(keyword)
                    || car.getColor().toLowerCase().contains(lowerKeyword)
                    || car.getBodyType().toLowerCase().contains(lowerKeyword)
                    || car.getEngineType().toLowerCase().contains(lowerKeyword)
                    || car.getTransmission().toLowerCase().contains(lowerKeyword)) {
                result.add(car);
            }
        }
        return result;
    }

    public int getLastId() {
        if (carList.isEmpty()) {
            return 0;
        }
        return carList.get(carList.size() - 1).getCarId();
    }

    public void writeToDisk() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Car car : carList) {
                bw.write(car.getCarId() + ","
                        + car.getLicensePlateNumber() + ","
                        + car.getMake() + ","
                        + car.getModel() + ","
                        + car.getYear() + ","
                        + car.getColor() + ","
                        + car.getBodyType() + ","
                        + car.getEngineType() + ","
                        + car.getTransmission());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving cars to disk: " + e.getMessage());
        }
    }

    public void readFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Cars database file not found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 9) continue;

                Car car = new Car();
                car.setCarId(Integer.parseInt(data[0]));
                car.setLicensePlateNumber(Integer.parseInt(data[1]));
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
            System.out.println("Error reading cars from disk: " + e.getMessage());
        }
    }
}
