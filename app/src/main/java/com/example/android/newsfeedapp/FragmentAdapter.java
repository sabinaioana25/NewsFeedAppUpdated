package com.example.android.newsfeedapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Sabina on 9/30/2017.
 */

class FragmentAdapter extends FragmentPagerAdapter {
    public Context context;

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentTechnology();
            case 1:
                return new FragmentHealthcare();
            case 2:
                return new FragmentScience();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.fragmentTechnology);
        } else if (position == 1) {
            return context.getString(R.string.fragmentHealthcare);
        } else {
            return context.getString(R.string.fragmentScience);
        }
    }
}
