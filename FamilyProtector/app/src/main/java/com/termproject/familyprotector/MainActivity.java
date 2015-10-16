package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button logout;
    EditText etUsername;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        userLocalStore = new UserLocalStore(this);
        displayUserDetails();
        logout.setOnClickListener(this);

    }
    private void init(){
        logout = (Button)findViewById(R.id.bLogout);
        etUsername = (EditText)findViewById(R.id.etUsername);

    }
    private void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();
        etUsername.setText(user.username);
    }
    @Override
    public void onClick(View view){
        userLocalStore.clearUserData();
        userLocalStore.setUserLoggedIn(false);
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }

}
