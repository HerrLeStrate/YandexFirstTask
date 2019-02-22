package me.herrlestrate.kadushko_artyom_info.fragments.welcome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import me.herrlestrate.kadushko_artyom_info.R;
import me.herrlestrate.kadushko_artyom_info.WelcomeActivity;

public class ThirdFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.i("WelcomePage","onCreateView thirdFragment");
        View result = inflater.inflate(R.layout.welcome_page_3,container,false);
        RelativeLayout relativeLayout = result.findViewById(R.id.welcome_rel3);

        RadioGroup radioGroup = relativeLayout.findViewById(R.id.welcome_radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.white_theme:
                        setTheme(false);
                        break;
                    case R.id.dark_theme:
                        setTheme(true);
                        break;
                    default:
                        setTheme(false);
                }
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
