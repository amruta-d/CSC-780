package com.termproject.familyprotector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class ChildDetailPagerAdapter extends FragmentStatePagerAdapter{
    private int numOfTabs;

    public ChildDetailPagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
        Log.v("in adapter", "tabs"+this.numOfTabs);
    }
    @Override
    public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    ChildAlertFragment childAlertFragment = new ChildAlertFragment();
                    Log.v("in adapter", "childalert");
                    return childAlertFragment;
                case 1:
                    ChildRuleFragment childRuleFragment = new ChildRuleFragment();
                    Log.v("in adapter", "child rule");
                    return childRuleFragment;
                default:
                    Log.v("in adapter", "in default");
                    return null;
            }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
