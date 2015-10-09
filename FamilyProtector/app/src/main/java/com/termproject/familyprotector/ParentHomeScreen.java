package com.termproject.familyprotector;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ParentHomeScreen extends AppCompatActivity implements View.OnClickListener{

    private Button AddChild;
    int noOfChildren;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home_screen);
        checkChildDetails();
        init();

        AddChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(ParentHomeScreen.this, AddChildDetails.class);
                startActivity(intent);
            }
        });


    }

    private void init() {
        AddChild = (Button) findViewById(R.id.AddChild);
    }

    private void checkChildDetails() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("ChildDetails");
        query.whereEqualTo("username", Login.user.username);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> children, ParseException e) {
                if (e == null) {
                    if (children.size() > 0) {
                        for (int i = 0; i < children.size(); i++) {
                            ParseObject child = children.get(i);
                            showChildButton(child, i+1);

                        }


                    } else {

                    }
                } else {
                    Log.d("Login", "Error: " + e.getMessage());
                }

            }
        });
    }

    private void showChildButton(ParseObject child, int buttonId){
        Drawable icon = getResources().getDrawable(R.drawable.child_boy_icon);
        if (child.getString("gender").equals("Female")) {
            icon = getResources().getDrawable(R.drawable.child_girl_icon);
        }
        icon.setBounds(0,0,100,100);
        Button childButton = new Button(this);
        childButton.setId(buttonId);
        childButton.setText(child.getString("name"));
        childButton.setBackgroundColor(Color.parseColor("#c5c1c1"));

        childButton.setCompoundDrawables(icon,null,null,null);
        LinearLayout ll = (LinearLayout)findViewById(R.id.linear_layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,0,8);
        ll.addView(childButton, lp);
        childButton.setOnClickListener(this);
    }

    public void onClick(View view){


    }


}
