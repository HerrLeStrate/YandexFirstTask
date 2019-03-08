package me.herrlestrate.kadushko_artyom_info.fragments.launcher;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import me.herrlestrate.kadushko_artyom_info.Consts;
import me.herrlestrate.kadushko_artyom_info.R;

public class LinearLauncherFragment extends Fragment {
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;

    public void onCreate(Bundle savedInstanceState){
        sharedPreferences = getActivity().getSharedPreferences("me.herrlestrate.kadushko_artyom_info_preferences",0);

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

        final PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent,0);
        int sortMethod = Integer.parseInt(sharedPreferences.getString("sort","0"));

        switch (sortMethod){
            case 0:
                break;
            case 1:
                Collections.sort(activities, new ResolveInfo.DisplayNameComparator(packageManager));
                break;
            case 2:
                Collections.sort(activities, new ResolveInfo.DisplayNameComparator(packageManager));
                Collections.reverse(activities);
                break;
            case 3:
                Collections.sort(activities, new Comparator<ResolveInfo>() {
                    @Override
                    public int compare(ResolveInfo o1, ResolveInfo o2) {
                        long a;
                        try {
                            a = packageManager.getPackageInfo(o1.activityInfo.packageName,0).firstInstallTime;
                        } catch (PackageManager.NameNotFoundException e) {
                            a = new Date().getTime();
                        }
                        long b;
                        try {
                            b = packageManager.getPackageInfo(o2.activityInfo.packageName,0).firstInstallTime;
                        } catch (PackageManager.NameNotFoundException e) {
                            b = new Date().getTime();
                        }
                        return Long.compare(a, b);
                    }
                });
                break;
            case 4:
                Collections.sort(activities, new Comparator<ResolveInfo>() {
                    @Override
                    public int compare(ResolveInfo o1, ResolveInfo o2) {
                        return Integer.compare(
                                Consts.get(o1.activityInfo.packageName),
                                Consts.get(o2.activityInfo.packageName)
                        );
                    }
                });
                break;
        }

        recyclerView.setAdapter(new LinearRecyclerViewAdapter(activities,getActivity()));
    }

    public static LinearLauncherFragment newInstance(){
        return new LinearLauncherFragment();
    }
}
