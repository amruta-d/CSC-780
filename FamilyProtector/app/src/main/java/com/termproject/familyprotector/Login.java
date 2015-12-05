package com.termproject.familyprotector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class Login extends Activity implements View.OnClickListener{
    Button bLogin;
    EditText etUsername, etPassword;
    TextView invalidLogin;
    String username,password;
    boolean validCredentials;
    User loggenInUser;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        userLocalStore = new UserLocalStore(this);
        bLogin.setOnClickListener(this);
    }

    private void init(){
        bLogin = (Button)findViewById(R.id.bLogin);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        invalidLogin = (TextView)findViewById(R.id.textview_invalid_login);

    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bLogin:
                checkCredentials();
           //     Log.d("Login", String.valueOf(validCredentials));
//                try {
//                    if (validCredentials) {
//                        loggenInUser = new User(username,password);
//                        userLocalStore.storeUserData(user);
//                        userLocalStore.setUserLoggedIn(true);
//                        startActivity(new Intent(this, ChooseMode.class));
//                    }
//                    else {
//                        outputError();
//                    }
//
//                }
//                catch (Exception e){
//                    Log.v("Login",e.toString());
//                }
//                if (validCredentials) {
//                    startActivity(new Intent(Login.this, ChooseMode.class));
//                }
//                else {
//                    outputError();
//                }
                break;
        }
    }

    private void checkCredentials() {

        username = etUsername.getText().toString();
        password = etPassword.getText().toString();



     //   user = new User(username,password);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserCredentials");
        query.whereEqualTo("username", username);
        query.whereEqualTo("password", password);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> users, ParseException e) {
                if (e == null) {
                    if (users.size() > 0) {
                        Log.v("abc",String.valueOf(users.size()));
//                        Log.v("login method",String.valueOf(users.size()));
                        for(int i= 0;i<users.size();i++){
                            ParseObject user = users.get(i);
                            Log.v("Login",user.getString("username")+" "+user.getString("password"));

                        }
                        //validCredentials = true;

                        loggenInUser = new User(username,password);
                     //   Log.v("Login",User.username+" "+User.password);

                        userLocalStore.storeUserData();
                       userLocalStore.setUserLoggedIn(true);
                        startActivity(new Intent(Login.this, ChooseMode.class));

                    } else {
                        validCredentials = false;
                    }
                } else {
                    Log.d("Login", "Error: " + e.getMessage());
                }

            }
        });
    }
    private void outputError(){
        invalidLogin.setText("Invalid credentials. Please enter correct credentials to login");


    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(this,WelcomePage.class));

    }



}
