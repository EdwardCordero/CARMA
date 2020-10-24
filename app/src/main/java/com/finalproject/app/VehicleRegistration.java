package com.finalproject.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VehicleRegistration extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    //Variables for Vehicle Registration form
    private EditText vr_Make, vr_Model, vr_Year, vr_BodyStyle, vr_CurrentMiles, vr_LicensePlate,
            vr_OilGrade, vr_OilBrand, vr_OilChangeMiles,
            vr_TireRotationMiles, vr_TireServiceType, vr_TireDiameter,
            vr_InsurancePolicyNum, vr_RegistrationExperation;
    private Button vr_Cancel, vr_Save, vr_AddVehicle;

    //Creates Vehicle Registration Page and button to add Vehicles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_registration);

        vr_AddVehicle = (Button) findViewById(R.id.AddCarButton);
        vr_AddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterNewVehicle();
            }
        });
    }

    //Creates popup when user clicks button to add vehicle
    public void RegisterNewVehicle() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View registervehiclePopup = getLayoutInflater().inflate(R.layout.vrpopup, null);
        
        //creates text boxes of form
        vr_Make = (EditText) registervehiclePopup.findViewById(R.id.Make);
        vr_Model = (EditText) registervehiclePopup.findViewById(R.id.Model);
        vr_Year = (EditText) registervehiclePopup.findViewById(R.id.Year);
        vr_BodyStyle = (EditText) registervehiclePopup.findViewById(R.id.BodyStyle);
        vr_CurrentMiles = (EditText) registervehiclePopup.findViewById(R.id.CurrentMiles);
        vr_LicensePlate = (EditText) registervehiclePopup.findViewById(R.id.LicensePlate);
        vr_OilGrade = (EditText) registervehiclePopup.findViewById(R.id.OilGrade);
        vr_OilBrand = (EditText) registervehiclePopup.findViewById(R.id.OilBrand);
        vr_OilChangeMiles = (EditText) registervehiclePopup.findViewById(R.id.OilChangeMiles);
        vr_TireRotationMiles = (EditText) registervehiclePopup.findViewById(R.id.TireRotationMiles);
        vr_TireServiceType = (EditText) registervehiclePopup.findViewById(R.id.TireServiceType);
        vr_TireDiameter = (EditText) registervehiclePopup.findViewById(R.id.TireDiameter);
        vr_InsurancePolicyNum = (EditText) registervehiclePopup.findViewById(R.id.InsurancePolicyNum);
        vr_RegistrationExperation = (EditText) registervehiclePopup.findViewById(R.id.RegistrationExperation);

        //creates buttons on form
        vr_Cancel = (Button) registervehiclePopup.findViewById(R.id.CancelButton);
        vr_Save = (Button) registervehiclePopup.findViewById(R.id.SaveButton);

        dialogBuilder.setView(registervehiclePopup);
        dialog = dialogBuilder.create();
        dialog.show();

        //Still need to make save function
        vr_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define save button here!!

            }
        });


        //closes popup when user clicks cancel.
        vr_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define cancel button here!!
                dialog.dismiss();
            }
        });
    }
}