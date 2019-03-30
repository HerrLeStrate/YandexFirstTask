package me.herrlestrate.kadushko_artyom_info.fragments.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import me.herrlestrate.kadushko_artyom_info.LauncherActivity;
import me.herrlestrate.kadushko_artyom_info.R;

public class ForthFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.i("WelcomePage","onCreateView forthFragment");
        final View result = inflater.inflate(R.layout.welcome_page_4,container,false);
        result.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LauncherActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        setStyle(false);

        result.findViewById(R.id.radioButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RadioButton)result.findViewById(R.id.radioButton2)).setChecked(false);
                setStyle(false);
            }
        });

        result.findViewById(R.id.radioButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RadioButton)result.findViewById(R.id.radioButton)).setChecked(false);
                setStyle(true);
            }
        });

        return result;
    }

    public static ForthFragment newInstance(){
        Log.i("WelcomePage","getInstance forthFragment");
        return new ForthFragment();
    }

    private void setStyle(boolean value){
        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences(
                "me.herrlestrate.kadushko_artyom_info_preferences",0).edit();
        editor.putBoolean("style",value);
        editor.commit();
    }
}
