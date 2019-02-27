package me.herrlestrate.kadushko_artyom_info.fragments.launcher;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import me.herrlestrate.kadushko_artyom_info.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState,
                                    String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        Preference myPref = findPreference("theme");
        Log.i("IMP",myPref.toString());
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                getActivity().recreate();
                return true;
            }
        });
    }

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }
}
