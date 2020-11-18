package com.finalproject.app.db;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    // User data
    String userFstName;
    String userLstName;
    String userName;
    String userEmail;

    // Default constructor
    // Required for calls to DataSnapshot.getValue(User.class)
    public User(){

    }

    // Constructor for all user arguments
    public User(String fname, String lname, String uname, String email){
        this.userFstName = fname;
        this.userLstName = lname;
        this.userName = uname;
        this.userEmail = email;
    }

    // Constructor for just email arg
    public User(String email){
        this.userEmail = email;
    }

    // methods
    public String getUserName(){
        return userName;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public String getUserFstName() { return userFstName; }

    public String getUserLstName() { return userLstName; }

    public String getFullName() {
        return  userFstName + " " + userLstName;
    }


}
