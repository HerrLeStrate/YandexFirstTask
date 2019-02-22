package me.herrlestrate.kadushko_artyom_info.fragments.welcome;

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

public class ForthFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.i("WelcomePage","onCreateView forthFragment");
        View result = inflater.inflate(R.layout.welcome_page_4,container,false);
        RelativeLayout relativeLayout = result.findViewById(R.id.welcome_rel4);

        RadioGroup radioGroup = relativeLayout.findViewById(R.id.welcome_radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.welcome_default:
                        setStyle(false);
                        break;
                    case R.id.welcome_full:
                        setStyle(true);
                        break;
                    default:
                        setStyle(false);
                }
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
