package com.finalproject.app;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 //* Use the {@link Reminder# newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reminder extends Fragment {

    ////////////////////////////////
    //added by Warren
    private PopupWindow popupwindow;
    private LayoutInflater layoutInflater;
    private ConstraintLayout constraintLayout; 
    ///////////////////////////////////////





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String mParam1;
    private String mParam2;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
    //
    //      Im not too sure how to mess with any of the stuff below so i left it alone for now
    //              -Warren
    //
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


        View view = inflater.inflate(R.layout.fragment_reminder, container, false);



        // declaring the buttons here.
        Button btnBrakes = view.findViewById(R.id.btnBrakes);
        Button btnFluids = view.findViewById(R.id.btnFluids);
        Button btnTires = view.findViewById(R.id.btnTires);
        Button btnBelts = view.findViewById(R.id.btnBelts);
        Button btnAirFilter = view.findViewById(R.id.btnAirFilter);
        constraintLayout = view.findViewById(R.id.constraintLayout);

        // this is the height for the popup window. just place holders for now
        final int pop_width = 800;
        final int pop_height = 1000;

// the beginning of the button methods. im sure there is a neater way to do all of this

        btnBrakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop_up, null);

                popupwindow = new PopupWindow(container, pop_width,pop_height, true);
                popupwindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);

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

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        popupwindow.dismiss();
                        return true;
                    }
                });
                Toast.makeText(getContext(),"Fluids", Toast.LENGTH_SHORT).show();
            }
        });


        btnTires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop_up, null);

                popupwindow = new PopupWindow(container, pop_width, pop_height, true);
                popupwindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);

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


        btnBelts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop_up, null);

                popupwindow = new PopupWindow(container, pop_width, pop_height, true);
                popupwindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        popupwindow.dismiss();
                        return true;
                    }
                });
                Toast.makeText(getContext(),"Belts", Toast.LENGTH_SHORT).show();
            }
        });


        btnAirFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop_up, null);

                popupwindow = new PopupWindow(container, pop_width, pop_height, true);
                popupwindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);

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