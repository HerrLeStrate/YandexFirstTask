package me.herrlestrate.kadushko_artyom_info.fragments.launcher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import me.herrlestrate.kadushko_artyom_info.R;
import me.herrlestrate.kadushko_artyom_info.fragments.dekstop.DesktopFragment;

public class LauncherFragmentPagerAdapter extends FragmentPagerAdapter {
    private int[] welcomePageLayouts = new int[]{
            R.layout.activity_launcher,
            R.layout.desktop_layout,
            R.layout.activity_linear
    };

    public LauncherFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return GridLauncherFragment.newInstance();
            case 1:
                return DesktopFragment.newInstance();
            case 2:
                return LinearLauncherFragment.newInstance();
            default:
                break;
        }
        Log.i("Error","Fragment not found exception");

        return null;
    }

    @Override
    public int getItemPosition(Object object){
        System.out.println("UPDATED");
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return this.welcomePageLayouts.length;
    }
}
