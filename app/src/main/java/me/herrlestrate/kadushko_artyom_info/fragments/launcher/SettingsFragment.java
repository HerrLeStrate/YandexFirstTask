package me.herrlestrate.kadushko_artyom_info.fragments.launcher;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import me.herrlestrate.kadushko_artyom_info.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState,
                                    String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }
}
