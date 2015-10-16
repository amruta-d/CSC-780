package com.termproject.familyprotector;

/**
 * Created by Mehul on 10/8/2015.
 */
public class User {
    public static String username, password;

    public User(String username, String password){
        User.username = username;
        User.password = password;
    }

    public String getUsername(){
        return User.username;
    }

    public String getPassword(){
        return User.password;

    }


}
