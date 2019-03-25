package me.herrlestrate.kadushko_artyom_info.fragments.launcher;

import android.content.Intent;
import android.content.IntentFilter;
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

import com.yandex.metrica.YandexMetrica;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import me.herrlestrate.kadushko_artyom_info.ApplicationBroadcaster;
import me.herrlestrate.kadushko_artyom_info.BackgroundManager;
import me.herrlestrate.kadushko_artyom_info.BackgroundReceiver;
import me.herrlestrate.kadushko_artyom_info.Consts;
import me.herrlestrate.kadushko_artyom_info.R;

public class LinearLauncherFragment extends Fragment {
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private ApplicationBroadcaster applicationBroadcaster;
    protected BackgroundReceiver mBackgroundReceiver;

    public void onCreate(Bundle savedInstanceState){
        sharedPreferences = getActivity().getSharedPreferences("me.herrlestrate.kadushko_artyom_info_preferences",0);

        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View result = inflater.inflate(R.layout.activity_linear,container,false);

        recyclerView = result.findViewById(R.id.linear_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        applicationBroadcaster = new ApplicationBroadcaster(getContext(),recyclerView);

        setupAdapter();

        String path = recyclerView.getContext().getFilesDir().toString()  + this.getClass().toString() + ".png";
        BackgroundManager.startJobService(recyclerView,path);
        mBackgroundReceiver = new BackgroundReceiver(recyclerView,path);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);

        filter.addDataScheme("package");

        getContext().registerReceiver(applicationBroadcaster, filter);

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
                YandexMetrica.reportEvent("Selected standard method sort");
                break;
            case 1:
                Collections.sort(activities, new ResolveInfo.DisplayNameComparator(packageManager));
                YandexMetrica.reportEvent("Selected sort by name");
                break;
            case 2:
                Collections.sort(activities, new ResolveInfo.DisplayNameComparator(packageManager));
                Collections.reverse(activities);
                YandexMetrica.reportEvent("Selected reverse sort by name");
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
                YandexMetrica.reportEvent("Selected sort by install date");
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
                YandexMetrica.reportEvent("Selected sort by frequency");
                break;
        }

        recyclerView.setAdapter(new MyRecyclerViewAdapter(activities,getActivity(),false));
    }

    public static LinearLauncherFragment newInstance(){
        return new LinearLauncherFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart Linear");
        IntentFilter filter = new IntentFilter(BackgroundReceiver.UPDATE_BACKGROUND);
        filter.addAction(BackgroundReceiver.UPDATE_BACKGROUND_ONCE);
        getContext().registerReceiver(mBackgroundReceiver,filter);
        getContext().sendBroadcast(new Intent(BackgroundReceiver.UPDATE_BACKGROUND));
    }

    /*@Override
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(mBackgroundReceiver);
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(applicationBroadcaster);

    }
}
