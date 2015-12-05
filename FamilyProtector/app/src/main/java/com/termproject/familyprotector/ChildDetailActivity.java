package com.termproject.familyprotector;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ChildDetailActivity extends AppCompatActivity{
//    TextView childNameTextView;
    String childNameStr;
//    FloatingActionButton addRuleFloatingActionButton;
    UserLocalStore userLocalStore;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_detail);
        mTabLayout = (TabLayout)findViewById(R.id.child_detail_tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Alerts"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Rules"));
        mTabLayout.setTabTextColors(Color.WHITE, Color.BLACK);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        childNameTextView = (TextView)findViewById(R.id.child_detail_text_view);
//        addRuleFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_rule_floating_action_button);
        userLocalStore = new UserLocalStore(this);
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.child_detail_pager);
        Log.v("child detail", "initialized viewPager");
       ChildDetailPagerAdapter mPagerAdapter = new ChildDetailPagerAdapter(getSupportFragmentManager(),mTabLayout.getTabCount());
        Log.v("child detail", "set pager adapet");
        Intent intent = getIntent();
        if(intent !=null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            childNameStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            Log.v("childName", childNameStr);
//            childNameTextView.setText(childNameStr);
            userLocalStore.setChildDetails(childNameStr);

        }

        mViewPager.setAdapter(mPagerAdapter);
        Log.v("child detail", "set pager adapter");
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        addRuleFloatingActionButton.setOnClickListener(this);
    }

//    public void onClick(View view) {
//        if (view.getId() == R.id.add_rule_floating_action_button) {
//            startActivity(new Intent(this, MapsActivity.class));
//
//
//
//        }
//    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }



}
