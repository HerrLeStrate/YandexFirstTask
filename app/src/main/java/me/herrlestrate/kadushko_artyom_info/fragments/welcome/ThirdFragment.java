package me.herrlestrate.kadushko_artyom_info.fragments.welcome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import me.herrlestrate.kadushko_artyom_info.Consts;
import me.herrlestrate.kadushko_artyom_info.R;

public class ThirdFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.i("WelcomePage","onCreateView thirdFragment");
        final View result = inflater.inflate(R.layout.welcome_page_3,container,false);
        result.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Consts.getWelcomePageAdapter().setCurrentItem(Consts.getWelcomePageAdapter().getCurrentItem()+1);
            }
        });

        setTheme(false);

        result.findViewById(R.id.radioButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheme(false);
            }
        });

        result.findViewById(R.id.radioButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheme(true);
            }
        });

        return result;
    }

    public static ThirdFragment newInstance(){
        Log.i("WelcomePage","getInstance thirdFragment");
        return new ThirdFragment();
    }

    private void setTheme(boolean value){
        //false = white, true = dark
        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences(
                "me.herrlestrate.kadushko_artyom_info_preferences",0).edit();
        editor.putBoolean("theme",value);
        editor.commit();
    }
}
