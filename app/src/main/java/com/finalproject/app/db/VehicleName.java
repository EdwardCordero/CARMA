package com.finalproject.app.db;

import com.finalproject.app.Garage;

public class VehicleName {
    private String name;

    public VehicleName() {

    }

    public VehicleName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void SetName(String name){
        this.name = name;
    }
}
