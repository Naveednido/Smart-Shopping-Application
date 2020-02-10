package com.example.smartshoppingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


// This class is used for setting fragment on the main activity in a PageView


class SectionPagerAdapter extends FragmentPagerAdapter {
    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Brands();
            case 1:
                return new Recommended_Products();
            case 2:
                return new Prize_Runner();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Brands";
            case 1:
                return "Recommended Products";
            case 2:
                return "Price Runner";
            default:
                return null;
        }
    }
}
