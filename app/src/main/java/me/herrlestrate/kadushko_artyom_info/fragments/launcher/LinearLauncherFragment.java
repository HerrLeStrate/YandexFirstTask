package me.herrlestrate.kadushko_artyom_info.fragments.launcher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.herrlestrate.kadushko_artyom_info.R;

public class LinearLauncherFragment extends Fragment {
    private RecyclerView recyclerView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View result = inflater.inflate(R.layout.activity_linear,container,false);

        recyclerView = result.findViewById(R.id.linear_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setupAdapter();

        return result;

    }

    public void setupAdapter(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent,0);

        recyclerView.setAdapter(new LinearRecyclerViewAdapter(activities,getActivity()));
    }

    public static LinearLauncherFragment newInstance(){
        return new LinearLauncherFragment();
    }
}
