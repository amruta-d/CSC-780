package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ParentHomeScreen extends AppCompatActivity {

    private Button AddChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home_screen);
        init();
        AddChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(ParentHomeScreen.this, AddChildDetails.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        AddChild = (Button)findViewById(R.id.AddChild);
    }

}
