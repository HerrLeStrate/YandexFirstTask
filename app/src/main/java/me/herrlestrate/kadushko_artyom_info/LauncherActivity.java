package me.herrlestrate.kadushko_artyom_info;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.crashlytics.android.Crashlytics;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

import java.net.URI;

import io.fabric.sdk.android.Fabric;
import me.herrlestrate.kadushko_artyom_info.fragments.SQLLiteWorker;
import me.herrlestrate.kadushko_artyom_info.fragments.dekstop.DesktopFragment;
import me.herrlestrate.kadushko_artyom_info.fragments.launcher.GridLauncherFragment;
import me.herrlestrate.kadushko_artyom_info.fragments.launcher.LauncherFragmentPagerAdapter;
import me.herrlestrate.kadushko_artyom_info.fragments.launcher.LinearLauncherFragment;
import me.herrlestrate.kadushko_artyom_info.fragments.launcher.ProfilerFragment;
import me.herrlestrate.kadushko_artyom_info.fragments.launcher.SettingsFragment;

public class LauncherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager vp;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Consts.setActivity(this);

        changeTheme();

        Log.i("LauncherActivityE","onCreate "+Consts.getLastFragment());

        Fabric.with(this, new Crashlytics());
        AppCenter.start(getApplication(), "7adff281-c7b5-4c1a-918e-f370ded917af", Analytics.class, Crashes.class);

        Consts.initSQL(getApplicationContext());

        /*// Создание расширенной конфигурации библиотеки.
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(API_key).build();
        // Инициализация AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config);
        // Отслеживание активности пользователей.
        YandexMetrica.enableActivityAutoTracking(this);*/

        setContentView(R.layout.activity_nav_drawer);

        vp = findViewById(R.id.launcher_view_pager);
        LauncherFragmentPagerAdapter adapter = new LauncherFragmentPagerAdapter(getSupportFragmentManager());
        Consts.setDesktopPagerAdapter(adapter);
        vp.setOffscreenPageLimit(adapter.getCount());
        vp.setAdapter(Consts.getDesktopPagerAdapter());


        setStarted(true);

        printSavedInfo();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationView.setCheckedItem(R.id.nav_none);
                YandexMetrica.reportEvent("ViewPager changed!");
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
                vp.setCurrentItem(0);
                YandexMetrica.reportEvent("Set Grid screen");

                break;
            case R.id.nav_list:
                Consts.setLastFragment(R.id.nav_list);
                vp.setCurrentItem(2);
                YandexMetrica.reportEvent("Set navigation screen");
                break;
            case R.id.desktop_list:
                Consts.setLastFragment(R.id.desktop_list);
                vp.setCurrentItem(1);
                YandexMetrica.reportEvent("Set desktop screen");
                break;
            case R.id.nav_settings:
                Consts.setLastFragment(R.id.desktop_list);
                startActivity(new Intent(this, SettingsActivity.class));
                YandexMetrica.reportEvent("Set settings screen");
                break;
            case R.id.notif_1:
                Intent notificationIntent = new Intent(this, LauncherActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(this,
                        0, notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationCompat.Builder notif;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("first","first", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(channel);
                    notif = new NotificationCompat.Builder(this,"first");
                } else {
                    notif = new NotificationCompat.Builder(this);
                }
                notif.setContentIntent(contentIntent)
                        .setSmallIcon(R.drawable.notif1)
                        .setContentTitle("Notif #1")
                        .setContentText("It is notif #1")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
                NotificationManagerCompat.from(this).notify(0,notif.build());
                break;
            case R.id.notif_2:
                Intent notificationIntent2 = new Intent(this, MainActivity.class);
                PendingIntent contentIntent2 = PendingIntent.getActivity(this,
                        0, notificationIntent2,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationCompat.Builder notif2;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("first","first", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(channel);
                    notif2 = new NotificationCompat.Builder(this,"first");
                } else {
                    notif2 = new NotificationCompat.Builder(this);
                }
                notif2.setContentIntent(contentIntent2)
                        .setSmallIcon(R.drawable.notif2)
                        .setContentTitle("Notif #2")
                        .setContentText("It is notif #2")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
                NotificationManagerCompat.from(this).notify(0,notif2.build());
                break;
            case R.id.nav_none:
                break;
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
