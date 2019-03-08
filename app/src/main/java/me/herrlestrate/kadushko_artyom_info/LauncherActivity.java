package me.herrlestrate.kadushko_artyom_info;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.net.URI;

import io.fabric.sdk.android.Fabric;
import me.herrlestrate.kadushko_artyom_info.fragments.SQLLiteWorker;
import me.herrlestrate.kadushko_artyom_info.fragments.launcher.GridLauncherFragment;
import me.herrlestrate.kadushko_artyom_info.fragments.launcher.LinearLauncherFragment;
import me.herrlestrate.kadushko_artyom_info.fragments.launcher.ProfilerFragment;
import me.herrlestrate.kadushko_artyom_info.fragments.launcher.SettingsFragment;

public class LauncherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        changeTheme();

        Log.i("LauncherActivityE","onCreate "+Consts.getLastFragment());

        Fabric.with(this, new Crashlytics());
        AppCenter.start(getApplication(), "7adff281-c7b5-4c1a-918e-f370ded917af", Analytics.class, Crashes.class);
        Consts.initSQL(getApplicationContext());

        setContentView(R.layout.activity_nav_drawer);

        setStarted(true);

        printSavedInfo();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationView.setCheckedItem(R.id.nav_none);
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_none));
            }
        });

        onNavigationItemSelected(navigationView.getMenu().findItem(Consts.getLastFragment()));

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        int id = menuItem.getItemId();
        Log.i("LauncherActivity","Navigation Item ID: "+Integer.toString(id));
        switch(id){
            case R.id.nav_grid:
                Consts.setLastFragment(R.id.nav_grid);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.launcher_container_fragments, GridLauncherFragment.newInstance())
                        .commit();
                break;
            case R.id.nav_list:
                Consts.setLastFragment(R.id.nav_list);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.launcher_container_fragments, LinearLauncherFragment.newInstance())
                        .commit();
                break;
            case R.id.nav_settings:
                Consts.setLastFragment(R.id.nav_settings);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.launcher_container_fragments, SettingsFragment.newInstance())
                        .commit();
                break;
            case R.id.nav_none:
                Consts.setLastFragment(R.id.nav_none);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.launcher_container_fragments, ProfilerFragment.newInstance())
                        .commit();
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void printSavedInfo(){
        SharedPreferences sharedPreferences = getSharedPreferences("me.herrlestrate.kadushko_artyom_info_preferences",0);
        Log.i("Debug",Boolean.toString(sharedPreferences.getBoolean("alreadyRunned",false)));
        Log.i("Debug",Boolean.toString(sharedPreferences.getBoolean("theme",false)));
        Log.i("Debug",Boolean.toString(sharedPreferences.getBoolean("style",false)));
    }

    public void changeTheme(){
        if(!getSharedPreferences("me.herrlestrate.kadushko_artyom_info_preferences",0)
                .getBoolean("theme",false))setTheme(R.style.AppTheme);
        else setTheme(R.style.AppThemeDark);
    }

    public void setStarted(boolean value){
        SharedPreferences sharedPreferences = getSharedPreferences("me.herrlestrate.kadushko_artyom_info_preferences",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("alreadyRunned",value);
        editor.commit();
    }
}
