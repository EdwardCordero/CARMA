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
    public void SetName(String Username){
        this.Username = Username;
    }

    public String getUserEmail(){
        return Email;
    }
    public void SetUserEmail(String Email){
        this.Email = Email;
    }

    public String getUserFstName() { return FirstName; }
    public void SetUserFstName(String FirstName){
        this.FirstName = FirstName;
    }
    public String getUserLstName() { return LastName; }
    public void SetUserLstName(String LastName){
        this.LastName = LastName;
    }
    public String getFullName() { return FirstName + " " + LastName; }
}
