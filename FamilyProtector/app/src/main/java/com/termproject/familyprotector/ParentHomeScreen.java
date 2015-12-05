package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ParentHomeScreen extends AppCompatActivity {

    private Button buttonAddChild;
    int noOfChildren;
    private android.support.v7.widget.Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home_screen);
        buttonAddChild = (Button) findViewById(R.id.AddChild);
        mRecyclerView = (RecyclerView)findViewById(R.id.parent_screen_recycler_view);
        userLocalStore = new UserLocalStore(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getChildrenDetailsFromParse();


        try {

            mToolBar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            Log.e("Parent screen", e.toString());
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerRoot);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolBar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        NavigationView nv = (NavigationView) findViewById(R.id.navView);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                String txt;
                switch (menuItem.getItemId()) {
                    case R.id.logout_drawer:
                        userLocalStore.setUserLoggedIn(false);

                        startActivity(new Intent(ParentHomeScreen.this, Login.class));
                        break;
                    default:
                        txt = "Invalid Item Selected";
                        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG).show();
                }

                mDrawerLayout.closeDrawers();
                return true;
            }
        });



        buttonAddChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(ParentHomeScreen.this, AddChildDetails.class);
                startActivity(intent);
            }
        });


    }


    private void getChildrenDetailsFromParse() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("ChildDetails");
        query.whereEqualTo("username", User.username);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> children, ParseException e) {
                if (e == null) {
                    if (children.size() > 0) {

                        mAdapter = new RecyclerAdapter (ParentHomeScreen.this, children);
                        mRecyclerView.setAdapter(mAdapter);


                    }


//                        for (int i = 0; i < children.size(); i++) {
//                            ParseObject child = children.get(i);
////                            showChildButton(child, i + 1);
//
//                        }
//
//
//                    } else {
//
//                    }
                } else {
                    Log.d("Login", "Error: " + e.getMessage());
                }

            }
        });
    }

//    private void showChildButton(ParseObject child, int buttonId) {
//        Drawable icon = getResources().getDrawable(R.drawable.child_boy_icon);
//        if (child.getString("gender").equals("Female")) {
//            icon = getResources().getDrawable(R.drawable.child_girl_icon);
//        }
//        icon.setBounds(0, 0, 100, 100);
//        Button childButton = new Button(this);
//        childButton.setId(buttonId);
//        childButton.setText(child.getString("name"));
//        childButton.setBackgroundColor(Color.parseColor("#F98F22"));
//
//        childButton.setCompoundDrawables(icon, null, null, null);
//        LinearLayout ll = (LinearLayout) findViewById(R.id.linear_layout);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(0, 0, 0, 8);
//        ll.addView(childButton, lp);
//        childButton.setOnClickListener(this);
//    }
//
//    public void onClick(View view) {
//
//
//
//    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


}
