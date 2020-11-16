package com.finalproject.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reminder#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Reminder extends Fragment {
    //Database References
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = mDatabase.getReference();
    DatabaseReference userCarsRef = mRef.child("user-cars");
    DatabaseReference uid = userCarsRef.child("ZAU5uoNDoaXX9nQHlp44Ddz2ATX2");
    DatabaseReference secondId = uid.child("-MM1ydgiob5OdsW77k2f");
    DatabaseReference name = secondId.child("name");

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
        final Button btnInfo = (Button)v.findViewById(R.id.btnInfo);
        final TextView currentMileage = (TextView)v.findViewById(R.id.textViewCurrentMileage);
        final TextView recentMaintenance = (TextView)v.findViewById(R.id.textViewRecentMain);
        final TextView upcomingMaintenance = (TextView)v.findViewById(R.id.textViewUpcomingMaintenance);

        // Static Number for mileage for testing purposes
        final int mileage = 25000;

        getCurrentMileage(currentMileage,mileage);
        recentMaintenance.setText(recentEveryFiveThousand());
        upcomingMaintenance.setText(everyThirtyThousand(mileage));

//        btnInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                name.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String data = snapshot.getValue().toString();
//                        recentMaintenance.setText(data);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//            }
//        });

        return v;
    }

    // Methods used to display messages into the TextView objects
    public void getCurrentMileage(TextView currentMileage, int mileage){
        TextView thisCurrentMileage = currentMileage;
        String theCurrentMileage = Integer.toString(mileage);
        currentMileage.setText("The current mileage on your vehicle is "
                + theCurrentMileage + " miles.");
    }

    public void recentMaintenance(TextView recentMain){
        recentMain.setText("");
    }

    public void getUpcomingMaintenance(TextView upcomingMain){
        upcomingMain.setText("");
    }

    // Methods for upcoming maintenance schedule
    public String everyFiveThousand(){
        return "• Change Oil and Filter\n" +
                "• Rotate/Balance Tires\n" +
                "• Inspect Battery/Clean contacts\n" +
                "• Inspect Fluids\n" +
                "• Inspect Hoses\n" +
                "• Inspect Tire Inflation/Condition";
    }

    public String everyFifteenThousand(){
        return "• Wheel Alignment\n" +
                "• Replace Wiper Blades\n" +
                "• Replace Engine Air Filter\n" +
                "• Inspect Rotors and Pads";
    }

    public String everyThirtyThousand(int mileage){
        String nextMainMileage = Integer.toString(30000 - mileage);

        return "Next maintenance is in " + nextMainMileage + " miles :\n" +
                "• Replace Battery (if needed)\n" +
                "• Inspect/Replace Cabin Air Filter\n" +
                "• Inspect/ Replace Brake Fluid\n" +
                "• Change Brake Pads/Rotors (if needed)";
    }

    public String everySixtyThousand(){
        return "• Inspect Timing Belt\n" +
                "• Inspect/Replace Serpentine Belts\n" +
                "• Inspect Transmission Fluid";
    }

    public String everyHundredTwentyThousand(){
        return "• Change Timing Belt\n" +
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

}