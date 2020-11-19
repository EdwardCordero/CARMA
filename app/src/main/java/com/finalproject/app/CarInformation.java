package com.finalproject.app;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject.app.auth.AuthUiActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarInformation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarInformation extends Fragment {

    ////////////////////////////////
    ///// Global Variables ////////
    private PopupWindow popupwindow;
    private LayoutInflater layoutInflater;
    private ConstraintLayout constraintLayout;
    DatabaseReference reference;
    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    final int pop_width = 800;
    final int pop_height = 1000;
    String selectedCar = "-MMNFXzSZ3NGJ6lHxpWu";
    ////////////////////////////////


    ///////////////////////////////////////
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarInformation() {

        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CarInformation newInstance(String param1, String param2) {
        CarInformation fragment = new CarInformation();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_information, container, false);
        constraintLayout = view.findViewById(R.id.CarInformationLayout);

        // connection to database
        reference = FirebaseDatabase.getInstance().getReference("user-cars");


        ///// this function works just need the other part
        // final DatabaseReference secondRef = reference.getRef().child(currentUser.getUid());
        ////// this function will be replaced once we get the car selection to work
        final DatabaseReference secondRef = reference.getRef().child("ZAU5uoNDoaXX9nQHlp44Ddz2ATX2");


        /////// declaring the buttons and textviews/////////
        Button btnBrakes = view.findViewById(R.id.btnBrakes);
        Button btnFluids = view.findViewById(R.id.btnFluids);
        Button btnTires = view.findViewById(R.id.btnTires);
        Button btnRecalls = view.findViewById(R.id.btnRecalls);
        Button btnAirFilter = view.findViewById(R.id.btnAirFilter);
        final TextView currentMileage = view.findViewById(R.id.currentMileage);

        ////////////////////////////////////////////////////




        // current user information to display top right corner
        final TextView userinfo = view.findViewById(R.id.usertextview);

        //////////////////////////////////////////////////////////



        secondRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // static variable for testing


                String mileage = snapshot.child(selectedCar).child("mileage").getValue(String.class);
                currentMileage.setText(mileage + " miles");
                String topRight1 = snapshot.child(selectedCar).child("make").getValue(String.class);
                String topRight2 = snapshot.child(selectedCar).child("model").getValue(String.class);
                String topRight3 = snapshot.child(selectedCar).child("licensePlate").getValue(String.class);


                userinfo.setText(topRight1 + "  " + topRight2 + "\n" + topRight3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





// the beginning of the button methods

        btnBrakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop_up, null);

                popupwindow = new PopupWindow(container, pop_width,pop_height, true);
                popupwindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);


                //
                //Setting information

                TextView infoTitle = (TextView)container.findViewById(R.id.infoTitle);
                final TextView infoDetails = (TextView) container.findViewById(R.id.infoDetails);
                TextView infoDate = (TextView)container.findViewById(R.id.infoDate);

                /* ready for when we get information on breaks in the database
               secondRef.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      String brakeDetails = snapshot.child("oilChange").getValue(String.class);
                      String brakeDetails2 = snapshot.child("oilGrade").getValue(String.class);

                      infoDetails.setText(brakeDetails + "\n" + brakeDetails2);


                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });*/

                // static information for now
                infoTitle.setText("Brakes");
                infoDetails.setText("Current car's brakes");
                infoDate.setText("August 04, 2019");

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        popupwindow.dismiss();
                        return true;
                    }
                });
                Toast.makeText(getContext(),"Brakes", Toast.LENGTH_SHORT).show();
            }
        });


        btnFluids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop_up, null);

                popupwindow = new PopupWindow(container, pop_width, pop_height, true);
                popupwindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);

                //
                // setting information
                TextView infoTitle = (TextView)container.findViewById(R.id.infoTitle);
                final TextView infoDetails = (TextView)container.findViewById(R.id.infoDetails);
                final TextView infoDate = (TextView)container.findViewById(R.id.infoDate);
                infoTitle.setText("Engine Oil");


                secondRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String oildetails = snapshot.child(selectedCar).child("oilChange").getValue(String.class);
                        String oildetails2 = snapshot.child(selectedCar).child("oilGrade").getValue(String.class);
                        infoDetails.setText("Oil Change: " + oildetails + "\n" + "Oil Grade: " + oildetails2 );

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                infoDate.setText("March 19, 2019");
                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        popupwindow.dismiss();
                        return true;
                    }
                });
                Toast.makeText(getContext(),"Engine Oil", Toast.LENGTH_SHORT).show();
            }
        });


        btnTires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop_up, null);

                popupwindow = new PopupWindow(container, pop_width, pop_height, true);
                popupwindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);


                //
                // setting information
                TextView infoTitle = (TextView)container.findViewById(R.id.infoTitle);
                final TextView infoDetails = (TextView)container.findViewById(R.id.infoDetails);
                TextView infoDate = (TextView)container.findViewById(R.id.infoDate);
                infoTitle.setText("Tires");

                secondRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String tireDetails1 = snapshot.child(selectedCar).child("tireDiameter").getValue(String.class);
                        String tireDetails2 = snapshot.child(selectedCar).child("tireRotation").getValue(String.class);
                        String tireDetails3 = snapshot.child(selectedCar).child("tireService").getValue(String.class);
                        infoDetails.setText("tire Diameter: " + tireDetails1 + "\n" + "tire Rotation: " + tireDetails2 +"\n"
                                + "Tire Service: "+ tireDetails3);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                infoDetails.setText("Michelin Defender T+H ");
                infoDate.setText("January 17, 2019");

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        popupwindow.dismiss();
                        return true;
                    }
                });
                Toast.makeText(getContext(),"Tires", Toast.LENGTH_SHORT).show();
            }
        });


        btnRecalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop_up, null);

                popupwindow = new PopupWindow(container, pop_width, pop_height, true);
                popupwindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);


                //
                // setting information
                TextView infoTitle = (TextView)container.findViewById(R.id.infoTitle);
                TextView infoDetails = (TextView)container.findViewById(R.id.infoDetails);
                TextView infoDate = (TextView)container.findViewById(R.id.infoDate);
                infoTitle.setText("Recalls");
                infoDetails.setText(" ");
                infoDate.setText(" ");

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        popupwindow.dismiss();
                        return true;
                    }
                });
                Toast.makeText(getContext(),"Recalls", Toast.LENGTH_SHORT).show();
            }
        });


        btnAirFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop_up, null);

                popupwindow = new PopupWindow(container, pop_width, pop_height, true);
                popupwindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);

                TextView infoTitle = (TextView)container.findViewById(R.id.infoTitle);
                TextView infoDetails = (TextView)container.findViewById(R.id.infoDetails);
                TextView infoDate = (TextView)container.findViewById(R.id.infoDate);
                infoTitle.setText("Air filter");
                infoDetails.setText("STP Air Filter SA9711 ");
                infoDate.setText("December 05, 2019");


                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        popupwindow.dismiss();

                        return true;
                    }
                });
                Toast.makeText(getContext(),"Air Filter", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
}