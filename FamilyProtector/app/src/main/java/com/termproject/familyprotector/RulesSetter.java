package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RulesSetter extends AppCompatActivity implements View.OnClickListener{
    Button buttonLocationPerimeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rules_setter);
        buttonLocationPerimeter = (Button)findViewById(R.id.button_location_perimeter);
        buttonLocationPerimeter.setOnClickListener(this);

    }

    public void onClick(View view){

        startActivity(new Intent(this,MapsActivity.class));


    }
}
