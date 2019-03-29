package me.herrlestrate.kadushko_artyom_info.fragments.welcome;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.herrlestrate.kadushko_artyom_info.Consts;
import me.herrlestrate.kadushko_artyom_info.R;

public class FirstFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.i("WelcomePage","onCreateView firstFragment");
        final View result = inflater.inflate(R.layout.welcome_page_1,container,false);
        result.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Consts.getWelcomePageAdapter().setCurrentItem(Consts.getWelcomePageAdapter().getCurrentItem()+1);
            }
        });
        return result;
    }

    public static FirstFragment newInstance(){
        Log.i("WelcomePage","getInstance firstFragment");
        return new FirstFragment();
    }
}
