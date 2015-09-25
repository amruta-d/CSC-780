package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseMode extends AppCompatActivity {

    private Button ParentMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosemode);
        init();

        ParentMode.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(ChooseMode.this, ParentHomeScreen.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        ParentMode = (Button)findViewById(R.id.ParentMode);
    }




}
