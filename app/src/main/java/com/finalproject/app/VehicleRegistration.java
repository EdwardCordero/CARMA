package com.finalproject.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VehicleRegistration extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private Button RecallButton, WarrantyButton, Close_RecallButton, Close_WarrantyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_registration);

        RecallButton = (Button) findViewById(R.id.RecallsButton);
        RecallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecallPopup();
            }
        });

        WarrantyButton = (Button) findViewById(R.id.WarrantyButton);
        WarrantyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarrantyPopup();
            }
        });
    }

    public void RecallPopup() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View recallPopupView = getLayoutInflater().inflate(R.layout.recall_popup,null);

        //Creates Close Button on Recall List Pop-up
        Close_RecallButton = (Button) recallPopupView.findViewById(R.id.CloseButton_Recall);

        dialogBuilder.setView(recallPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        //Closes Recall list popup window when close button is clicked
        Close_RecallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void WarrantyPopup() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View warrantyPopupView = getLayoutInflater().inflate(R.layout.warranty_popup,null);

        //Creates Close Button on Warranty List Pop-up
        Close_WarrantyButton = (Button) warrantyPopupView.findViewById(R.id.CloseButton_Warranty);

        dialogBuilder.setView(warrantyPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        //Closes Warranty list popup window when close button is clicked
        Close_WarrantyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

}