package me.herrlestrate.kadushko_artyom_info.fragments.launcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.herrlestrate.kadushko_artyom_info.R;

public class ProfilerFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.activity_main,container,false);

        return result;
    }

    public static ProfilerFragment newInstance(){
        return new ProfilerFragment();
    }
}
