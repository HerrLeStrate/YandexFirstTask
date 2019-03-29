package me.herrlestrate.kadushko_artyom_info.fragments.welcome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import me.herrlestrate.kadushko_artyom_info.R;

public class WelcomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private int[] welcomePageLayouts = new int[]{
            R.layout.welcome_page_1,
            R.layout.welcome_page_2,
            R.layout.welcome_page_4,
            R.layout.welcome_page_3
    };

    public WelcomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return FirstFragment.newInstance();
            case 1:
                return SecondFragment.newInstance();
            case 2:
                return ThirdFragment.newInstance();
            case 3:
                return ForthFragment.newInstance();
            default:
                break;
        }

        Log.i("Error","Fragment not found exception");

        return null;
    }

    @Override
    public int getCount() {
        return this.welcomePageLayouts.length;
    }
}
