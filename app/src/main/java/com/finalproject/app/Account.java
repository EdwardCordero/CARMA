package com.finalproject.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;

import com.finalproject.app.db.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment {

    /***********************************
     * ACCOUNT FRAGMENT VARIABLES
     *************************************/
    // Buttons
    private Button btnChangePasswd;

    // Firebase Auth instance
    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Dialog builder for popup
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    // Text view for Account information
    private TextView userName, userEmail, userFullName;
    // Buttons for Account page
    private Button aboutButton;

    // Database Reference
    DatabaseReference userRef;

    public Account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Sets up database reference for user info

        final String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uID);

        userName = (TextView) view.findViewById(R.id.UserNameText);
        userEmail = (TextView) view.findViewById(R.id.UserEmailText);
        userFullName = (TextView) view.findViewById(R.id.FullNameText);

        //Buttons
        aboutButton = (Button) view.findViewById(R.id.AboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AboutPage();
            }
        });
        // Strings for database registration
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               String uName = snapshot.child("userName").getValue(String.class);
               userName.setText(uName);
               String uEmail = snapshot.child("userEmail").getValue(String.class);
               userEmail.setText(uEmail);
               String uFullName = snapshot.child("fullName").getValue(String.class);
               userFullName.setText(uFullName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


                passwordResetDialog.create().show();
            }
        }); // End reset password ClickListener
        return view;
    }

    public void AboutPage() {
        Button CloseAboutPage;
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View aboutPopup = getLayoutInflater().inflate(R.layout.about_popup, null);

        // Creates buttons on form
        CloseAboutPage = (Button) aboutPopup.findViewById(R.id.CloseButton_About);

        dialogBuilder.setView(aboutPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        // Closes popup when user clicks close button
        CloseAboutPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define cancel button here!!
                dialog.dismiss();
            }
        });
    }

};
