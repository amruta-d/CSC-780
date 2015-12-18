package com.termproject.familyprotector;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class ChildDetailActivity extends AppCompatActivity{

    String childNameStr;

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
        userLocalStore = new UserLocalStore(this);
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.child_detail_pager);
       ChildDetailPagerAdapter mPagerAdapter = new ChildDetailPagerAdapter(getSupportFragmentManager(),mTabLayout.getTabCount());
        Intent intent = getIntent();
        if(intent !=null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            childNameStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            userLocalStore.setChildDetails(childNameStr);

        }

        mViewPager.setAdapter(mPagerAdapter);
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

    }


    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }



}
