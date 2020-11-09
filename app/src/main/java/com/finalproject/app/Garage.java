package com.finalproject.app;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;

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

    private EditText vr_Make, vr_Model, vr_Year, vr_BodyStyle, vr_Mileage, vr_LicensePlate,
            vr_OilGrade, vr_OilChangeMiles,
            vr_TireRotationMiles, vr_TireServiceType, vr_TireDiameter,
            vr_InsurancePolicyNum, vr_RegistrationExperation;

    private Button vr_Cancel, vr_Save, vr_AddVehicle, recallButton, warrantyButton, CloseRecallButton, CloseWarrantyButton;
    //variables for card view menu
    private CardView card1;

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
    // TODO: Rename and change types and number of parameters
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
        View view = inflater.inflate(R.layout.fragment_garage, container, false);
        // Creates Spinner for car list
        Spinner carList = (Spinner) view.findViewById(R.id.CarList);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.CarList));
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        carList.setAdapter(myAdapter);

        //Creates button and adds listener
        vr_AddVehicle = (Button) view.findViewById(R.id.AddCarButton);
        vr_AddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterNewVehicle();
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