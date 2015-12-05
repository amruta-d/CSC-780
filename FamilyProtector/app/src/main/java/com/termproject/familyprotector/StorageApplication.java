package com.termproject.familyprotector;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by Mehul on 10/7/2015.
 */
public class StorageApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * The authentication key can be used for demo purposes. If you want to
         * use Parse's web interface to view the uploaded data online, you need
         * to register and use your own authentication key.
         */
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

     //   Parse.initialize(this, "J0mm3n5ZoyC6rZpPVZwtczV1EsoE3xs1jmYj5AsK", "FGvRp2j38OCHVuNmJhZEBPlJntZ0fR0sT5YWwaPG");
        Parse.initialize(this, "Vdtbz5r7WmY2BipSx2MvvkrVlIFmsYpLmyYxEDis", "PuWA8YcEGG5dZRmXlj0Ij5tdy1vO6SsdosDEOZBl");
//        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
//
//        installation.put("email", "parent1");
//        installation.saveInBackground();

        Log.v("WelcomePage", "I am here1");
        ParseUser.enableAutomaticUser();
        Log.v("WelcomePage", "I am here2");
        ParseACL defaultACL = new ParseACL();
        Log.v("WelcomePage", "I am here3");
        defaultACL.setPublicReadAccess(true);
        Log.v("WelcomePage", "I am here4");
        ParseACL.setDefaultACL(defaultACL, true);
        Log.v("WelcomePage", "I am here5");
    }
}
