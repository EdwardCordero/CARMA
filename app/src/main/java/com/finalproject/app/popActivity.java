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
    String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference carRef = mDatabase.getReference();
    DatabaseReference userCarRef = carRef.child("user-cars").child(user);
    DatabaseReference specificCarRef;
    DatabaseReference mileageRef;

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
                final EditText updateMileage = (EditText)findViewById(R.id.editTextUpdateMileage);
                userCarRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            String uid = ds.getKey();
                            specificCarRef = userCarRef.child(uid);
                            mileageRef = specificCarRef.child("mileage");

                            String newData = updateMileage.getText().toString();
                            mileageRef.setValue(newData);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
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