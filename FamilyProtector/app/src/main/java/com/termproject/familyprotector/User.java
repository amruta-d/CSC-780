package com.termproject.familyprotector;

/**
 * Created by Mehul on 10/8/2015.
 */
public class User {
    public String username, password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;

    }


}
