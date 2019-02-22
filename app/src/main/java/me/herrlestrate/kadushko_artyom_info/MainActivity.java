package me.herrlestrate.kadushko_artyom_info;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Fabric.with(this, new Crashlytics());
        AppCenter.start(getApplication(), "7adff281-c7b5-4c1a-918e-f370ded917af", Analytics.class, Crashes.class);
        setContentView(R.layout.activity_main);*/
        setContentView(R.layout.activity_nav_drawer);
    }
}
