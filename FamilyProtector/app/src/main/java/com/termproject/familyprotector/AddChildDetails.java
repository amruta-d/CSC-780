package com.termproject.familyprotector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.parse.ParseObject;

public class AddChildDetails extends Activity implements View.OnClickListener{
    EditText editTextChildName, editTextBirthDate;
    RadioButton radioButtonMale, radioButtonFemale;
    Button buttonSave;
    public static Child child;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_details);
        init();

        buttonSave.setOnClickListener(this);


//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void init(){

        editTextChildName = (EditText)findViewById(R.id.NameOfChild);
        editTextBirthDate = (EditText)findViewById(R.id.BirthDateText);
        radioButtonMale = (RadioButton)findViewById(R.id.radio_button_male);
        radioButtonFemale = (RadioButton)findViewById(R.id.radio_button_female);
        buttonSave = (Button)findViewById(R.id.button_save);
    }

    @Override
    public void onClick(View view){
        child = new Child(this);
        storeToParse();
        startActivity(new Intent(this,ParentHomeScreen.class));

    }

    private void storeToParse (){
        ParseObject childDetails = new ParseObject("ChildDetails");
        childDetails.put("username", User.username);
        childDetails.put("name",child.name);
        childDetails.put("gender", child.gender);
        childDetails.put("birthdate",child.birthdate);
        childDetails.saveInBackground();

    }

}
