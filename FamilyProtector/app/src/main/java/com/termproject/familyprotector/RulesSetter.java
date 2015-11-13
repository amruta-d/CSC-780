package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RulesSetter extends AppCompatActivity implements View.OnClickListener{
    Button buttonLocationPerimeter;
    private android.support.v7.widget.Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rules_setter);
        buttonLocationPerimeter = (Button)findViewById(R.id.button_location_perimeter);
        buttonLocationPerimeter.setOnClickListener(this);


        try {

            mToolBar = (Toolbar) findViewById(R.id.toolbar_rules_setter);
            setSupportActionBar(mToolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            Log.e("Rules Setter", e.toString());
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerRoot_rules_setter);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolBar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        NavigationView nv = (NavigationView) findViewById(R.id.navView_rules_setter);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                String txt;
                switch (menuItem.getItemId()) {
                    case R.id.logout_drawer:
                        startActivity(new Intent(RulesSetter.this, Login.class));
                        break;
                    default:
                        txt = "Invalid Item Selected";
                        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG).show();
                }

                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    public void onClick(View view){

        startActivity(new Intent(this,MapsActivity.class));


    }
}
