package com.finalproject.app;

public class CarRegistration {

    private String make;
    private String model;
    private String year;
    private String bodystyle;
    private String mileage;
    private String licensePlate;
    private String tireDiameter;
    private String tireService;
    private String tireRotation;
    private String oilGrade;
    private String oilChange;
    private String insuranceNum;
    private String registrationExp;

    public CarRegistration(){

    }

    public CarRegistration(String make, String model, String year, String bodystyle, String mileage, String licensePlate,
                           String tireDiameter, String tireService, String tireRotation, String oilGrade, String oilChange,
                           String insuranceNum, String registrationExp){
        this.make = make;
        this.model = model;
        this.year = year;
        this.bodystyle = bodystyle;
        this.mileage = mileage;
        this.licensePlate = licensePlate;
        this.tireDiameter = tireDiameter;
        this.tireService = tireService;
        this.tireRotation = tireRotation;
        this.oilGrade = oilGrade;
        this.oilChange = oilChange;
        this.insuranceNum = insuranceNum;
        this.registrationExp = registrationExp;

    }

    // Takes user input for vehicle information section and reads it
    public String getMake(){
        return make;
    }
    public String getModel(){
        return model;
    }
    public String getYear(){
        return year;
    }
    public String getBodystyle(){
        return bodystyle;
    }
    public String getMileage(){
        return mileage;
    }
    public String getLicensePlate(){
        return licensePlate;
    }

    public void SetVehicleInformation(String make, String model, String year, String bodystyle, String mileage, String licensePlate){
        this.make = make;
        this.model = model;
        this.year = year;
        this.bodystyle = bodystyle;
        this.mileage = mileage;
        this.licensePlate = licensePlate;
    }

    // Takes user input for Tire information section and reads it.

    public String getTireDiameter(){
        return tireDiameter;
    }
    public String getTireService(){
        return tireService;
    }
    public String getTireRotation(){
        return tireRotation;
    }

    public void SetTireInformation(String tireDiameter, String tireService, String tireRotation){
        this.tireDiameter = tireDiameter;
        this.tireService = tireService;
        this.tireRotation = tireRotation;
    }

    // Takes user input for Oil Information and reads it

    public String getOilGrade(){
        return oilGrade;
    }
    public String getOilChange(){
        return oilChange;
    }

    public void SetOilInformation(String oilGrade, String oilChange){
        this.oilGrade = oilGrade;
        this.oilChange = oilChange;
    }

    // Takes user input for Insurance Information and reads it

    public String getInsuranceNum(){
        return insuranceNum;
    }

    public String getRegistrationExp(){
        return registrationExp;
    }

    public void SetInsuranceInformation(String insuranceNum, String registrationExp){
        this.insuranceNum = insuranceNum;
        this.registrationExp = registrationExp;
    }
}
