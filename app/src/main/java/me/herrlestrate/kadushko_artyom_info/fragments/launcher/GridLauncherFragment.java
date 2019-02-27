package me.herrlestrate.kadushko_artyom_info.fragments.launcher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.herrlestrate.kadushko_artyom_info.R;

public class GridLauncherFragment extends Fragment {

    private RecyclerView recyclerView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View result = inflater.inflate(R.layout.activity_launcher,container,false);

        recyclerView = result.findViewById(R.id.icon_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),getInRow());

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),getInRow()));

        setupAdapter();

        int offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        recyclerView.addItemDecoration(new LauncherItemDecoration(offset));

        return result;

    }

    public int getInRow(){
        int result = 4;
        if(getActivity().getSharedPreferences("me.herrlestrate.kadushko_artyom_info_preferences",0)
                .getBoolean("style",false))result=5;
        return result;
    }

    public void setupAdapter(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent,0);

        recyclerView.setAdapter(new MyRecyclerViewAdapter(activities,getActivity()));
    }

    public static GridLauncherFragment newInstance(){
        return new GridLauncherFragment();
    }
}
