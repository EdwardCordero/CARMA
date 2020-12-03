package com.finalproject.app.db;

import com.finalproject.app.Garage;
import com.google.firebase.auth.FirebaseAuth;

public class User {
    // User data

    String FirstName;
    String LastName;
    String Username;
    String Email;

    // Default constructor
    // Required for calls to DataSnapshot.getValue(User.class)
    public User(){

    }

    // Constructor for all user arguments
    public User(String fname, String lname, String uname, String email){
        this.FirstName = fname;
        this.LastName = lname;
        this.Username = uname;
        this.Email = email;
    }

    // Constructor for just email arg
    public User(String email){
        this.Email = email;
    }

    // methods
    public String getUserName(){
        return Username;
    }

    public String getUserEmail(){
        return Email;
    }


    public String getUserFstName() { return FirstName; }

    public String getUserLstName() { return LastName; }

    public String getFullName() { return FirstName + " " + LastName; }




}
