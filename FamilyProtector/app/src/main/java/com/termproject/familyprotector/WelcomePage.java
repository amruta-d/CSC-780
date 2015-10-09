package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomePage extends AppCompatActivity {

    private Button Continue;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.v("WelcomePage","I am here");

        init();

        Continue.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(WelcomePage.this, Register.class);
                startActivity(intent);
            }
        });

    }

    private void init(){
        Continue = (Button)findViewById(R.id.Continue);
    }




}
