package com.finalproject.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.finalproject.app.auth.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if user is signed in
        // If not currently signed in then start the login activity
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startLoginActivity();
        }
        // Bottom Menu

        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new CarInformation()).commit();

    } // End onCreate

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment fragment=null;

                    switch(menuItem.getItemId())
                    {
                        case R.id.carInformation:
                            fragment=new CarInformation();
                            break;

                        case R.id.reminder:
                            fragment=new Reminder();
                            break;

                        case R.id.settings:
                            fragment=new Settings();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
                    return true;
                }
            };

    private void startLoginActivity(){
        // Create intent for AuthUI Login activity
        Intent intent = new Intent(this, LoginActivity.class);
        // Start the login activity
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    } // End onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.sign_out_menu:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                // sign out
                AuthUI.getInstance().signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    startLoginActivity();
                                }
                            }
                        });
                return true;
        }
        return super.onOptionsItemSelected(item);
    } // End onOptionsItemsSelected

} // End MainActivity