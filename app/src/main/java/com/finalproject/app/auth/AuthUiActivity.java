package com.finalproject.app.auth;


import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.finalproject.app.MainActivity;
import com.finalproject.app.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class AuthUiActivity extends AppCompatActivity {

    /***********************************
     * USER REGISTRATION VARIABLES
    *************************************/
    // Buttons
    private Button btnRegister;
    private Button btnLogin;
    private Button btnResetPassword;


    /***********************************
     * FIREBASE INSTANCE VARIABLES
     ************************************/
    private static final int RC_SIGN_IN = 100;
    // Authentication Providers
    private List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

    // Firebase Auth instance
    private  FirebaseAuth fbAuth;

    @NonNull
    public static Intent createIntent(@NonNull Context context){
        return new Intent(context, AuthUiActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_ui_layout);

        fbAuth = FirebaseAuth.getInstance();

        // Buttons
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnSignIn);
        btnResetPassword = findViewById(R.id.btnForgotPassword);

        // New user registration page
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthUiActivity.this, RegistrationActivity.class);
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
                Toast.makeText(this, "Signed in cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void handleLoginRegister() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com")
                        // Reserved for when our logo is finsihed
                        //.setLogo()
                        .setAlwaysShowSignInMethodScreen(true)
                        .build(),
                RC_SIGN_IN);
    }

} // End AuthUiActivity class