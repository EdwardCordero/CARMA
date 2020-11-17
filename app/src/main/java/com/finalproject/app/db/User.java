package com.finalproject.app.db;

import com.google.firebase.auth.FirebaseAuth;

@IgnoreExtraProperties
public class User {
    // User data
    String userName;
    String userEmail;

    // Default constructor
    // Required for calls to DataSnapshot.getValue(User.class)
    public User(){

    }

    // Constructor for both username and email arguments
    public User(String uname, String email){
        this.userName = uname;
        this.userEmail = email;
    }

    // Constructor for just email arg
    public User(String email){
        this.userEmail = email;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserEmail(){
        return userEmail;
    }

}
