package com.rhayven.car.model;

public class Car {
    private int carId;
    private String licensePlateNumber;
    private String make;
    private String model;
    private int year;
    private String color;
    private String bodyType;
    private String engineType;
    private String transmission;

    public Car() {
    }

    public Car(int carId, String licensePlateNumber, String make, String model, int year,
               String color, String bodyType, String engineType, String transmission) {
        this.carId = carId;
        this.licensePlateNumber = licensePlateNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.bodyType = bodyType;
        this.engineType = engineType;
        this.transmission = transmission;
    }

    // Getters and Setters
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
}
