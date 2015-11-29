package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WelcomePage extends AppCompatActivity implements View.OnClickListener{

    private Button Continue;
    UserLocalStore userLocalStore;
    User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //      Log.v("WelcomePage","I am here");

        init();
        userLocalStore = new UserLocalStore(this);

        Continue.setOnClickListener(this);

    }
//    @Override
//    protected void onStart(){
//        super.onStart();
//
//        if (authenticate()){
//
//        }
//
//
//    }
    private boolean authenticate(){

        return userLocalStore.getUserLoggedIn();

    }

    @Override
    public void onClick(View view) {

        boolean value = authenticate();
       Log.v("Welcome", String.valueOf(value));
        if (value) {
           // Log.v("welcome", "inside if loop");
            String appMode = userLocalStore.getAppMode();
            if(appMode.equals("parent")) {
                user = userLocalStore.getLoggedInUser();
                Intent intent = new Intent(this, ParentHomeScreen.class);
                startActivity(intent);
            }
            else if (appMode.equals("child")){
                user = userLocalStore.getLoggedInUser();
                Intent intent = new Intent(this, RulesSetter.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this, ChooseMode.class);
                startActivity(intent);
            }
        }
        else{
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);

        }
    }


    private void init(){
        Continue = (Button)findViewById(R.id.Continue);
    }




}
