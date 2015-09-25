package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WelcomePage extends AppCompatActivity {

    private Button Continue;
    private TextView WelcomeText,WelcomeText2;
    @Override
    public void onCreate(Bundle savedInstanceState) {



//        super.onCreate(savedInstanceState);
//        LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();


//        final TextView textView = new TextView(this);
//        params.leftMargin = params.rightMargin = params.topMargin = 50;
//        params.bottomMargin = 25;
//        params.gravity = Gravity.CENTER;
//        layout.addView(textView, params);
//        textView.setText("Welcome to Family Protector! \n Helps parents ensure the safety of their children..");

//        Button button = new Button(this);
//        button.setText("Continue");
        Continue.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(WelcomePage.this, ChooseMode.class);
                startActivity(intent);
            }
        });
//        layout.addView(button, params);
//        setContentView(layout);

    }

    private void init(){
        WelcomeText = (TextView)findViewById(R.id.WelcomeText);
        WelcomeText2 = (TextView)findViewById(R.id.WelcomeText2);
        Continue = (Button)findViewById(R.id.Continue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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
