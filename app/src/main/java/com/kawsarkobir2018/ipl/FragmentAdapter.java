package com.kawsarkobir2018.ipl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by HP-NPC on 14/11/2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new EarnFragment();
            case 1:
                return new HowToFragment();
            case 2:
                return new RefFragment();
            case 3:
                return new PayFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Earn";
            case 1:
                return "Guide";
            case 2:
                return "Refer";
            case 3:
                return "Payment";
            default:
                return null;
        }
    }
}
