package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class AddChildDetails extends AppCompatActivity {
    private TextView Header,ChildName, Gender, Birthday;
    private EditText NameOfChild,BirthDateText;
    private RadioButton Male,Female;
    private Button Save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_details);
        init();
        Save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(AddChildDetails.this, Test.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        Header = (TextView)findViewById(R.id.Header);
        ChildName = (TextView)findViewById(R.id.ChildName);
        Gender = (TextView)findViewById(R.id.Gender);
        Birthday = (TextView)findViewById(R.id.Birthday);
        NameOfChild = (EditText)findViewById(R.id.NameOfChild);
        BirthDateText = (EditText)findViewById(R.id.NameOfChild);
        Male = (RadioButton)findViewById(R.id.Male);
        Female = (RadioButton)findViewById(R.id.Female);
        Save = (Button)findViewById(R.id.SaveButton);
    }

}
