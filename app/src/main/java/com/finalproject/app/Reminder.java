package com.finalproject.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reminder#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Reminder extends Fragment {
    //Database References
    String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference carRef = mDatabase.getReference();
    DatabaseReference userCarRef = carRef.child("user-cars").child("0f4ag8w46qfwmBUf9FLmu2c4tl53");
//    DatabaseReference mileageRef = uid.child();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Reminder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reminder.
     */
    // TODO: Rename and change types and number of parameters
    public static Reminder newInstance(String param1, String param2) {
        Reminder fragment = new Reminder();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reminder, container, false);

        // Declare Buttons and TextViews elements from Reminders page
        final Button btnUpdateMileage = (Button)v.findViewById(R.id.btnUpdateMileage);
        final TextView currentMileage = (TextView)v.findViewById(R.id.textViewCurrentMileage);
        final TextView recentMaintenance = (TextView)v.findViewById(R.id.textViewRecentMain);
        final TextView upcomingMaintenance = (TextView)v.findViewById(R.id.textViewUpcomingMaintenance);

        // Allows textviews to be scrollable
        currentMileage.setMovementMethod(new ScrollingMovementMethod());
        recentMaintenance.setMovementMethod(new ScrollingMovementMethod());
        upcomingMaintenance.setMovementMethod((new ScrollingMovementMethod()));

        int initialMileage = 120000;
        final int mileageRightNow = 130000;


//         // Gets information from Database
//
//        userCarRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//               String data = (String) snapshot.getValue();
//               currentMileage.setText(data);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        while(true) {

            // Static Number for mileage for testing purpose
//            String currentUser = user.getUid();

            initialMileage = maintenanceRoutine(initialMileage,mileageRightNow,currentMileage,
                    recentMaintenance, upcomingMaintenance);

            // Gets information from Database

//        uid.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String data = snapshot.getValue().toString();
//                currentMileage.setText(data);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


            // Controls what happens when you click on the Update Mileage Button
            btnUpdateMileage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Pops Up a Window that asks to update mileage

                    Intent popUp = new Intent(getContext(), popActivity.class);
                    startActivity(popUp);

                }
            });

            return v;
        }

}

    // Methods used to display messages into the TextView objects
    public void getCurrentMileage(TextView currentMileage, int mileage){
        String theCurrentMileage = Integer.toString(mileage);
        currentMileage.setText(String.format("The current mileage on your vehicle is %s miles.",
                theCurrentMileage));
    }

    public void recentMaintenance(TextView recentMain){
        recentMain.setText("");
    }

    public void getUpcomingMaintenance(TextView upcomingMain){
        upcomingMain.setText("");
    }

    // Methods for upcoming maintenance schedule
    public String everyFiveThousand(int currentMileage){
        String nextUpcoming = Integer.toString(5000 - (currentMileage % 5000));
            return "Upcoming in " + nextUpcoming + " miles:\n" +
                    "• Change Oil and Filter\n" +
                    "• Rotate/Balance Tires\n" +
                    "• Inspect Battery/Clean contacts\n" +
                    "• Inspect Fluids\n" +
                    "• Inspect Hoses\n" +
                    "• Inspect Tire Inflation/Condition";
    }

    public String everyFifteenThousand(int currentMileage){
        String nextUpcoming = Integer.toString(15000 - (currentMileage % 15000));
        return "Upcoming in " + nextUpcoming + " miles:\n" +
                "• Wheel Alignment\n" +
                "• Replace Wiper Blades\n" +
                "• Replace Engine Air Filter\n" +
                "• Inspect Rotors and Pads";
    }

    public String everyThirtyThousand(int currentMileage){
        String nextUpcoming = Integer.toString(30000 - (currentMileage % 30000));

        return "Upcoming in " + nextUpcoming +  " miles:\n" +
                "• Replace Battery (if needed)\n" +
                "• Inspect/Replace Cabin Air Filter\n" +
                "• Inspect/ Replace Brake Fluid\n" +
                "• Change Brake Pads/Rotors (if needed)";
    }

    public String everySixtyThousand(int currentMileage){
        String nextUpcoming = Integer.toString(60000 - (currentMileage % 60000));
        return "Upcoming in " + nextUpcoming + " miles:\n" +
                "• Inspect Timing Belt\n" +
                "• Inspect/Replace Serpentine Belts\n" +
                "• Inspect Transmission Fluid";
    }

    public String everyHundredTwentyThousand(int currentMileage){
        String nextUpcoming = Integer.toString(120000 - (currentMileage % 120000));
        return "Upcoming in " + nextUpcoming + " miles:\n" +
                "• Change Timing Belt\n" +
                "• Change Spark Plugs\n" +
                "• Change Engine Coolant\n" +
                "• Change Transmission Fluid\n" +
                "• Change Power Steering Fluid";
    }

    // Methods for recent maintenance schedule
    public String recentEveryFiveThousand(){
        return "• Changed Oil and Filter\n" +
                "• Rotated/Balanced Tires\n" +
                "• Inspected Battery/Clean contacts\n" +
                "• Inspected Fluids\n" +
                "• Inspected Hoses\n" +
                "• Inspected Tire Inflation/Condition";
    }

    public String recentEveryFifteenThousand(){
        return "• Aligned Wheels\n" +
                "• Replaced Wiper Blades\n" +
                "• Replaced Engine Air Filter\n" +
                "• Inspected Rotors and Pads";
    }

    public String recentEveryThirtyThousand(){
        return "• Replaced Battery (if needed)\n" +
                "• Inspected/Replaced Cabin Air Filter\n" +
                "• Inspected/ Replaced Brake Fluid\n" +
                "• Changed Brake Pads/Rotors (if needed)";
    }

    public String recentEverySixtyThousand(){
        return "• Inspected Timing Belt\n" +
                "• Inspected/Replaced Serpentine Belts\n" +
                "• Inspected Transmission Fluid";
    }

    public String recentEveryHundredTwentyThousand(){
        return "• Changed the Timing Belt\n" +
                "• Changed the Spark Plugs\n" +
                "• Changed the Engine Coolant\n" +
                "• Changed the Transmission Fluid\n" +
                "• Changed the Power Steering Fluid";
    }

    public int maintenanceRoutine(int initialMileage, int currentMileage, TextView Mileage,
                                  TextView recentMaintenance, TextView upcomingMaintenance ) {

        getCurrentMileage(Mileage,currentMileage);

        if (currentMileage - initialMileage < 5000){
             upcomingMaintenance.setText(everyFiveThousand(currentMileage));
        }

        if (currentMileage - initialMileage >= 5000){
            upcomingMaintenance.setText(String.format("%s\n\n%s", everyFiveThousand(currentMileage),
                    everyFifteenThousand(currentMileage)));
            recentMaintenance.setText(recentEveryFiveThousand());
        }

        if (currentMileage - initialMileage >= 15000){
            upcomingMaintenance.setText(String.format("%s\n\n%s", everyFiveThousand(currentMileage),
                    everyThirtyThousand(currentMileage)));
            recentMaintenance.setText(recentEveryFifteenThousand());
        }

        if (currentMileage - initialMileage >= 30000){
            upcomingMaintenance.setText(String.format("%s\n\n%s",
                    everyFiveThousand(currentMileage),
                    everySixtyThousand(currentMileage)));
            recentMaintenance.setText(recentEveryThirtyThousand());
        }

        if (currentMileage - initialMileage >= 60000){
            upcomingMaintenance.setText(String.format("%s\n\n%s",
                    everyFiveThousand(currentMileage),
                    everyHundredTwentyThousand(currentMileage)));
            recentMaintenance.setText(recentEverySixtyThousand());
        }

        if (currentMileage - initialMileage >= 120000){
            upcomingMaintenance.setText(everyFiveThousand(currentMileage));
            recentMaintenance.setText(recentEveryHundredTwentyThousand());
            initialMileage = currentMileage;
            return initialMileage;
        }

        return initialMileage;
    }



}