package me.herrlestrate.kadushko_artyom_info.fragments.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import me.herrlestrate.kadushko_artyom_info.BackgroundReceiver;
import me.herrlestrate.kadushko_artyom_info.Consts;
import me.herrlestrate.kadushko_artyom_info.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState,
                                    String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        Preference myPref = findPreference("theme");
        Log.i("IMP",myPref.toString());
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                //Consts.getDesktopPagerAdapter().notifyDataSetChanged();
                //
                Consts.getActivity().recreate();
                getActivity().recreate();
                return true;
            }
        });
        Preference sorting = findPreference("sort");
        sorting.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                Consts.getDesktopPagerAdapter().notifyDataSetChanged();
                return true;
            }
        });
        Preference updateTime = findPreference("imageUpdateDelay");
        updateTime.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                Consts.getDesktopPagerAdapter().notifyDataSetChanged();
                return true;
            }
        });
        Preference updateNow = findPreference("imageUpdateNow");
        updateNow.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().sendBroadcast(new Intent(BackgroundReceiver.UPDATE_BACKGROUND_ONCE));
                Consts.getDesktopPagerAdapter().notifyDataSetChanged();
                return true;
            }
        });
    }

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }
}
