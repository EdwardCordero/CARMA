package com.finalproject.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class popActivity extends Activity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = mDatabase.getReference("user-cars");
    DatabaseReference uid = mRef.child("0f4ag8w46qfwmBUf9FLmu2c4tl53");
    DatabaseReference carRef = uid.child("-MMUCgPWu4hXNlVfDN7M");
    DatabaseReference mileageRef = carRef.child("mileage");

    Button btnClose;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);


        //Declaration for Cancel button and Click Listener to dismiss the pop up.
        btnClose = (Button)findViewById(R.id.buttonCancelUpdateMileage);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

         //Declaration for the update button.
        btnUpdate = (Button)findViewById(R.id.buttonUpdateMileagePop);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                EditText updateMileage = (EditText)findViewById(R.id.editTextUpdateMileage);
                String newMileage = updateMileage.getText().toString();
                mileageRef.setValue(Integer.valueOf(newMileage));
                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width *.6), (int)(height*.5));

        WindowManager.LayoutParams params =getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }
}