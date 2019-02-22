package me.herrlestrate.kadushko_artyom_info;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import me.herrlestrate.kadushko_artyom_info.fragments.welcome.WelcomeFragmentPagerAdapter;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i("WelcomePage","onCreate");

        if(alreadyRunned()){
            //TODO LauncherActivity
            nextActivity();
        }

        setContentView(R.layout.activity_welcome);

        final ViewPager viewPager = findViewById(R.id.welcome_view_pager);
        Button buttonNext = findViewById(R.id.welcome_btn_next);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                if(currentPage < Objects.requireNonNull(
                        viewPager.getAdapter()).getCount()-1)
                    viewPager.setCurrentItem(currentPage+1);
                else {
                    //TODO LauncherActivity.class
                    nextActivity();
                }
            }
        });

        viewPager.setAdapter(new WelcomeFragmentPagerAdapter(
                getSupportFragmentManager()
        ));
    }

    public boolean alreadyRunned(){
        SharedPreferences preferences = getSharedPreferences("me.herrlestrate.kadushko_artyom_info_preferences",0);
        return preferences.getBoolean("alreadyRunned",false);
    }

    public void nextActivity(){
        Intent intent = new Intent(this, LauncherActivity.class);
        startActivity(intent);
        finish();
    }

}
