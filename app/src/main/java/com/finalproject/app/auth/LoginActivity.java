package com.finalproject.app.auth;



import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.finalproject.app.MainActivity;
import com.finalproject.app.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    /***********************************
     * USER REGISTRATION VARIABLES
    *************************************/
    // Buttons
    private Button btnRegister;
    private Button btnLogin;
    // progress bar
    private ProgressBar loading_widget;
    // Text views
    private TextView resetPassword;
    // user data
    private EditText uEmail, uPassword;


    /***********************************
     * FIREBASE INSTANCE VARIABLES
     ************************************/
    private static final int RC_SIGN_IN = 100;
    // Firebase Auth instance
    private  FirebaseAuth fbAuth = FirebaseAuth.getInstance();

    /***********************************
     * FIREBASE DB Instance Variables
     ************************************/
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @NonNull
    public static Intent createIntent(@NonNull Context context){
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        // Buttons
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnSignIn);
        // Reset password
        resetPassword = findViewById(R.id.forgot_password);
        // User sign-in data
        uEmail = findViewById(R.id.sign_in_email_address);
        uPassword = findViewById(R.id.sign_in_password);
        // Progress bar
        loading_widget = findViewById(R.id.progressBarLogin);

        // New user registration page
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        // Returning user sign-in
       btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               handleLoginRegister();
           }
       });

        // Check if user is already logged in
        if (fbAuth.getCurrentUser() != null){
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }

        // Resetting the user password
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText userMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter your email address to receive a reset link.");
                passwordResetDialog.setView(userMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get the email from user and send a reset link
                        String mail = userMail.getText().toString();
                        fbAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Display message to user that link has been sent
                                Toast.makeText(LoginActivity.this, "Reset link sent to your email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Display message to user that link has not been sent
                                Toast.makeText(LoginActivity.this, "Error: Failed to send reset link - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Close dialog and go back to login page
                    }
                });

                passwordResetDialog.create().show();
            }
        }); // End reset password

    } // End onCreate

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // Checking if newly created user
                if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()){
                    Toast.makeText(this, "Welcome new User", Toast.LENGTH_SHORT).show();
                } else{
                    // Returning user
                    Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
                }
                // Log user in to main activity
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(this, LoginActivity.class));
                //finish();
            }
        }
    }

    public void handleLoginRegister() {
        // Getting user input
        final String signEmail = uEmail.getText().toString().trim();
        final String signPasswd = uPassword.getText().toString().trim();

        // Error checking for empty inputs
        if (TextUtils.isEmpty(signEmail)){
            uEmail.setError("Email is required.");
            return;
        }

        // Checking valid email address
        if(!Patterns.EMAIL_ADDRESS.matcher(signEmail).matches()){
            uEmail.setError("Please enter a valid email");
            uEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(signPasswd)){
            uPassword.setError("Password is required.");
            return;
        }

        // Show progress bar
        loading_widget.setVisibility(View.VISIBLE);

        // Authenticate user
        fbAuth.signInWithEmailAndPassword(signEmail, signPasswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loading_widget.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    // Display toast
                    Toast.makeText(LoginActivity.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                    // Log user in to main activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Display error
                    Toast.makeText(LoginActivity.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

} // End LoginActivity class