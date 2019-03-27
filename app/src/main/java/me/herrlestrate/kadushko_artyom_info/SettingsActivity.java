package me.herrlestrate.kadushko_artyom_info;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setTheme(Consts.getTheme(getSharedPreferences("me.herrlestrate.kadushko_artyom_info_preferences",0)));

        setContentView(R.layout.activity_settings);

    }
}
