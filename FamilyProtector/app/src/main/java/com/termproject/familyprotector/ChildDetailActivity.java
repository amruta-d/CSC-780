package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ChildDetailActivity extends AppCompatActivity implements View.OnClickListener{
    TextView childNameTextView;
    String childNameStr;
    FloatingActionButton addRuleFloatingActionButton;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_detail);
        childNameTextView = (TextView)findViewById(R.id.child_detail_text_view);
        addRuleFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_rule_floating_action_button);
        userLocalStore = new UserLocalStore(this);
        Intent intent = getIntent();
        if(intent !=null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            childNameStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            Log.v("childName", childNameStr);
            childNameTextView.setText(childNameStr);
            userLocalStore.setChildDetails(childNameStr);

        }
        addRuleFloatingActionButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.add_rule_floating_action_button) {
            startActivity(new Intent(this, MapsActivity.class));



        }
    }



}
