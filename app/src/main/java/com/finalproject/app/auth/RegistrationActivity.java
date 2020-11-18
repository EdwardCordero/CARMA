package com.finalproject.app.auth;

import com.finalproject.app.db.User;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.finalproject.app.MainActivity;
import com.finalproject.app.R;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class RegistrationActivity extends AppCompatActivity {
    /***********************************
     * USER REGISTRATION VARIABLES
     *************************************/
    private Button btnRgstrAccnt;
    private ProgressBar loadingCircle;

    // Register form input data
    EditText mFirstName, mLastName, mUserName, mUserEmail, mUserPassword, mConfirmPassword;


    /***********************************
     * FIREBASE INSTANCE VARIABLES
     ************************************/
    // Firebase Auth instance
    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();


    /***********************************
     * FIREBASE DB Instance Variables
     ************************************/
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users");

    @NonNull
    public static Intent createIntent(@NonNull Context context){
        return new Intent(context, RegistrationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_register_form);
        // Check if user is signed in already


        // Registration input data
        mFirstName = findViewById(R.id.firstname);
        mLastName = findViewById(R.id.lastname);
        mUserName = findViewById(R.id.username);
        mUserEmail = findViewById(R.id.email);
        mUserPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirm_password);
        loadingCircle = findViewById(R.id.progressBar);

        // Register new user account
        btnRgstrAccnt = findViewById(R.id.btnRegisterAccount);
        btnRgstrAccnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleNewUserRegistration();
            }
        });



    }// End onCreate


    // writeNewUser()
    // writes new user data to DB
    private void writeNewUser(String userID, String FirstName, String LastName, String UserName, String Email){
        User user = new User(FirstName, LastName, UserName, Email);
        rootRef.child(userID).setValue(user);
    }



    public void handleNewUserRegistration(){
        /*************************************************
        *  User Data gathered from registration form
        * ************************************************/
        final String uFstName = mFirstName.getText().toString().trim();
        final String uLstName = mLastName.getText().toString().trim();
        final String username = mUserName.getText().toString().trim();
        final String uEmail = mUserEmail.getText().toString().trim();
        String uPasswd = mUserPassword.getText().toString().trim();
        String confirmPasswd = mConfirmPassword.getText().toString().trim();

        // Query object for checking unique usernames
        Query usernameQuery = FirebaseDatabase.getInstance().getReference().child("Users");

        // Validate user data
        // No data entered for first and last name
        if(TextUtils.isEmpty(uFstName)){
            mFirstName.setError("First and Last name is required to create an account");
            mFirstName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(uLstName)){
            mLastName.setError("First and Last name is required to create an account");
            mLastName.requestFocus();
            return;
        }

        // Username is empty
        if(TextUtils.isEmpty(username)){
            mUserName.setError("username is required to create an account");
            mUserName.requestFocus();
            return;
        }

        // Check if username already exists in DB

        // No email entered
        if(TextUtils.isEmpty(uEmail)){
            mUserEmail.setError("Email is required to create an account");
            mUserEmail.requestFocus();
            return;
        }

        // Checking valid email address
        if(!Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()){
            mUserEmail.setError("Please enter a valid email");
            mUserEmail.requestFocus();
            return;
        }

        // No password is entered
        if(TextUtils.isEmpty(uPasswd)){
            mUserPassword.setError("Password is required");
            mUserPassword.requestFocus();
            return;
        }

        // Password not more than six characters
        if(uPasswd.length() < 6){
            mUserPassword.setError("Password must be more than 5 characters.");
            mUserPassword.requestFocus();
            return;
        }

        // Passwords don't match
        if(!uPasswd.equals(confirmPasswd)){
            mConfirmPassword.setError("Passwords do not match");
            return;
        }

        // Display loading process
        loadingCircle.setVisibility(View.VISIBLE);

        // Register the new user in firebase
        fbAuth.createUserWithEmailAndPassword(uEmail, uPasswd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loadingCircle.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    // Add user data to db
                    FirebaseUser newUser = task.getResult().getUser();
                    writeNewUser(newUser.getUid(), uFstName, uLstName, username, uEmail);
                    Toast.makeText(RegistrationActivity.this, "Registered Successfully.", Toast.LENGTH_SHORT).show();
                    // Log user in to main activity
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(RegistrationActivity.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}// end RegistrationActivity
