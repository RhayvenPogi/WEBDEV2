package com.rhayven.car.model;

/**
 * Represents a car with basic vehicle details.
 */
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

    /**
     * Default constructor.
     */
    public Car() {
    }

    /**
     * Constructs a car with all attributes.
     *
     * @param carId               unique identifier of the car
     * @param licensePlateNumber license plate number
     * @param make                manufacturer of the car
     * @param model               model of the car
     * @param year                manufacturing year
     * @param color               color of the car
     * @param bodyType            type of body (e.g. sedan, SUV)
     * @param engineType          engine type (e.g. petrol, electric)
     * @param transmission        transmission type (e.g. automatic, manual)
     */
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

    /**
     * Returns the car ID.
     */
    public int getCarId() {
        return carId;
    }

    /**
     * Sets the car ID.
     */
    public void setCarId(int carId) {
        this.carId = carId;
    }

    /**
     * Returns the license plate number.
     */
    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    /**
     * Sets the license plate number.
     */
    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    /**
     * Returns the make of the car.
     */
    public String getMake() {
        return make;
    }

    /**
     * Sets the make of the car.
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Returns the model of the car.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the car.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Returns the manufacturing year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the manufacturing year.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the color of the car.
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of the car.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Returns the body type of the car.
     */
    public String getBodyType() {
        return bodyType;
    }

    /**
     * Sets the body type of the car.
     */
    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    /**
     * Returns the engine type.
     */
    public String getEngineType() {
        return engineType;
    }

    /**
     * Sets the engine type.
     */
    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    /**
     * Returns the transmission type.
     */
    public String getTransmission() {
        return transmission;
    }

    /**
     * Sets the transmission type.
     */
    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
}
