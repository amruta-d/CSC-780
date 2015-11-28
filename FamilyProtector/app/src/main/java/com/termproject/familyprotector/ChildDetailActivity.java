package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ChildDetailActivity extends AppCompatActivity {
    TextView childNameTextView;
    String childNameStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_detail);
        childNameTextView = (TextView)findViewById(R.id.child_detail_text_view);
        Intent intent = getIntent();
        if(intent !=null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            childNameStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            Log.v("childName",childNameStr);
            childNameTextView.setText(childNameStr);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_child_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
