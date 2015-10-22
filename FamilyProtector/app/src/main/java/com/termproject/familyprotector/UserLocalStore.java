package com.termproject.familyprotector;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){

        try {

            userLocalDatabase = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
           // Log.v("UserLocalStore","done with initialize");
        }
        catch (Exception e){
           // Log.v("UserLocalStore",e.toString());
        }

    }

    public void storeUserData(){
        Log.v("storeUserData",User.username+" "+User.password);
        try {
            SharedPreferences.Editor spEditor = userLocalDatabase.edit();

            spEditor.putString("username", User.username);
            spEditor.putString("password", User.password);
            spEditor.commit();
        }
        catch (Exception e){
            Log.v("UserLocalStore",e.toString());

        }
    }
    public User getLoggedInUser(){
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password","");
        User storedUser = new User(username,password);
        return storedUser;
    }
    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }
    public boolean getUserLoggedIn(){
        if (userLocalDatabase.getBoolean("loggedIn",false)==true){
            return true;
        }
        else{
            return false;
        }

    }
    public void setAppMode(String appMode){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("appMode", appMode);
        spEditor.commit();
    }
    public String getAppMode(){


        String appMode = userLocalDatabase.getString("appMode","");
        return appMode;

    }
    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();

    }
}
