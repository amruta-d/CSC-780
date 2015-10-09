package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseObject;

public class Register extends AppCompatActivity implements View.OnClickListener{

    Button bRegister;
    EditText etUsername, etPassword;
    String username,password;
    TextView textSignIn;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

      //  Log.v("Register",username);

      //  user = new User(username,password);

        bRegister.setOnClickListener(this);
        textSignIn.setOnClickListener(this);

    }

    private void init(){
        bRegister = (Button)findViewById(R.id.bRegister);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        textSignIn = (TextView)findViewById(R.id.text_sign_in);

    }

    @Override

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bRegister:
                getDetails();
                break;
            case R.id.text_sign_in:
                startActivity(new Intent(this,Login.class));
                break;
        }
    }

    private void getDetails(){
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        storeToParse();
        startActivity(new Intent(this, ChooseMode.class));


    }

    private void storeToParse (){
        ParseObject userCredentials = new ParseObject("UserCredentials");
        userCredentials.put("username",username);
        userCredentials.put("password", password);
        userCredentials.saveInBackground();

    }

}
