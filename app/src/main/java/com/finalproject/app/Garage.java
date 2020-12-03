package com.finalproject.app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.finalproject.app.db.User;
import com.finalproject.app.db.VehicleName;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Garage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Garage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Variables for Vehicle Registration form
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    // Variables for Text in vehicle registration popup page
    private EditText vr_Make, vr_Model, vr_Year, vr_BodyStyle, vr_Mileage, vr_LicensePlate,
            vr_OilGrade, vr_OilChangeMiles,
            vr_TireRotationMiles, vr_TireServiceType, vr_TireDiameter,
            vr_InsurancePolicyNum, vr_RegistrationExperation;
    // Buttons
    private Button vr_Cancel, vr_Save, vr_AddVehicle, recallButton, warrantyButton, CloseRecallButton, CloseWarrantyButton;

    //Variables for garage text view
    private TextView make, model, year, bodystyle, mileage, licensePlate, oilGrade, oilChange, tireRotation, tireService,
                     tireDiameter, insuranceNum, registrationExp;
    // Variables for Spinner name function
    private EditText sp_Name;
    // Buttons in spinner
    private Button sp_Save, sp_Cancel;

    // Database Reference
    DatabaseReference carRef;


    public Garage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Garage.
     */

    public static Garage newInstance(String param1, String param2) {
        Garage fragment = new Garage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_garage, container, false);

        // Sets up database for car registration
        carRef = FirebaseDatabase.getInstance().getReference();

        // initializes text for garage
        make = (TextView) view.findViewById(R.id.MakeText);
        model = (TextView) view.findViewById(R.id.ModelText);
        year = (TextView) view.findViewById(R.id.YearText);
        bodystyle = (TextView) view.findViewById(R.id.BodyStyleText);
        mileage = (TextView) view.findViewById(R.id.MileageText);
        licensePlate = (TextView) view.findViewById(R.id.LicensePlateText);
        insuranceNum = (TextView) view.findViewById(R.id.InsurancePolicyNumText);
        registrationExp = (TextView) view.findViewById(R.id.RegistrationExperationText);

        //Creates button and adds listener
        vr_AddVehicle = (Button) view.findViewById(R.id.AddCarButton);
        vr_AddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameVehicle();
            }
        });
        //Creates listener for both Recall and Warranty buttons to show popups
        recallButton = (Button) view.findViewById(R.id.RecallsButton);
        recallButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               RecallButtonListener();
            }
        });

        warrantyButton = (Button) view.findViewById(R.id.WarrantyButton);
        warrantyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                WarrantyButtonListener();
            }
        });
    // Strings for database registration
        final String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final Spinner spinnerCar = (Spinner) view.findViewById(R.id.CarList);
        final DatabaseReference userCarRef = carRef.child("user-cars").child(uID);
        final ArrayList<CarRegistration> cars = new ArrayList<>();

        userCarRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> carCollectionList = new ArrayList<>();

                for (DataSnapshot carSnapshot: snapshot.getChildren()) {
                    CarRegistration carInfo = carSnapshot.getValue(CarRegistration.class);
                    carCollectionList.add(carInfo.getName());

                    cars.add(carInfo);
                }
                ArrayAdapter<String> carAdapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, carCollectionList);
                carAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinnerCar.setAdapter(carAdapter);

                // Sets up spinner onClick event
                spinnerCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                        String makeDB = cars.get(position).getMake();
                        make.setText(makeDB);
                        String modelDB = cars.get(position).getModel();
                        model.setText(modelDB);
                        String yearDB = cars.get(position).getYear();
                        year.setText(yearDB);
                        String bodystyleDB = cars.get(position).getBodystyle();
                        bodystyle.setText(bodystyleDB);
                        String mileageDB = cars.get(position).getMileage();
                        mileage.setText(mileageDB);
                        String licenseplateDB = cars.get(position).getLicensePlate();
                        licensePlate.setText(licenseplateDB);
                        String insurancenumDB = cars.get(position).getInsuranceNum();
                        insuranceNum.setText(insurancenumDB);
                        String registrationexpDB = cars.get(position).getRegistrationExp();
                        registrationExp.setText(registrationexpDB);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

    //Creates popup when user clicks on Recall button
    public void RecallButtonListener() {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View recallPopup = getLayoutInflater().inflate(R.layout.recall_popup, null);

        //creates buttons on form
        CloseRecallButton = (Button) recallPopup.findViewById(R.id.CloseButton_Recall);

        dialogBuilder.setView(recallPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        //closes popup when user clicks close.
        CloseRecallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define cancel button here!!
                dialog.dismiss();
            }
        });
    }

    //Creates popup when user clicks on Warranty button
    public void WarrantyButtonListener() {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View warrantyPopup = getLayoutInflater().inflate(R.layout.warranty_popup, null);

        //creates buttons on form
        CloseWarrantyButton = (Button) warrantyPopup.findViewById(R.id.CloseButton_Warranty);

        dialogBuilder.setView(warrantyPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        //closes popup when user clicks close.
        CloseWarrantyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define cancel button here!!
                dialog.dismiss();
            }
        });
    }


    // Creates popup to name the vehicle the user will register
    public void NameVehicle(){
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View namevehiclePopup = getLayoutInflater().inflate(R.layout.spinnername_popup, null);

        //Creates the text box for the name
        sp_Name = (EditText) namevehiclePopup.findViewById(R.id.VehicleName);
        //Creates the save button
        sp_Save = (Button) namevehiclePopup.findViewById(R.id.SaveNameButton);
        sp_Cancel = (Button) namevehiclePopup.findViewById(R.id.CancelNameButton);

        dialogBuilder.setView(namevehiclePopup);
        dialog = dialogBuilder.create();
        dialog.show();

        //Saves name to spinner string array and adds it to the spinner
        sp_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                RegisterNewVehicle();
            }
        });
        //closes popup when user clicks cancel.
        sp_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define cancel button here!!
                dialog.dismiss();
            }
        });
    }

    //Creates popup when user clicks button to add vehicle
    public void RegisterNewVehicle() {

        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View registervehiclePopup = getLayoutInflater().inflate(R.layout.vrpopup, null);

        //creates text boxes of form
        vr_Make = (EditText) registervehiclePopup.findViewById(R.id.Make);
        vr_Model = (EditText) registervehiclePopup.findViewById(R.id.Model);
        vr_Year = (EditText) registervehiclePopup.findViewById(R.id.Year);
        vr_BodyStyle = (EditText) registervehiclePopup.findViewById(R.id.BodyStyle);
        vr_Mileage = (EditText) registervehiclePopup.findViewById(R.id.Mileage);
        vr_LicensePlate = (EditText) registervehiclePopup.findViewById(R.id.LicensePlate);
        vr_OilGrade = (EditText) registervehiclePopup.findViewById(R.id.OilGrade);
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

        // Strings for database registration
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String key = carRef.child("Cars").push().getKey();

        // save function
        vr_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarRegistration carRegistration = new CarRegistration(sp_Name.getText().toString(),vr_Make.getText().toString(), vr_Model.getText().toString(), vr_Year.getText().toString(),
                        vr_BodyStyle.getText().toString(), vr_Mileage.getText().toString(), vr_LicensePlate.getText().toString(), vr_OilChangeMiles.getText().toString(),
                        vr_OilGrade.getText().toString(), vr_TireRotationMiles.getText().toString(), vr_TireServiceType.getText().toString(), vr_TireDiameter.getText().toString(),
                        vr_InsurancePolicyNum.getText().toString(), vr_RegistrationExperation.getText().toString());

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/Cars/" + key, carRegistration);
                childUpdates.put("/user-cars/" + userID + "/" + key, carRegistration);

                carRef.updateChildren(childUpdates);
                dialog.dismiss();
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