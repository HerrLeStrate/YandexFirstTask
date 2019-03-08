package me.herrlestrate.kadushko_artyom_info.fragments.launcher;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.herrlestrate.kadushko_artyom_info.R;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<LauncherViewHolder> {

    private final List<ResolveInfo> mInstalledAppInfo;
    private final Map<ResolveInfo, Drawable> mIcons;
    private final FragmentActivity activity;
    private boolean isGrid = true;

    public MyRecyclerViewAdapter(List<ResolveInfo> installedAppsInfo, FragmentActivity fActivity, boolean isGrid) {
        mInstalledAppInfo =  installedAppsInfo;
        mIcons = new HashMap<>();
        activity=fActivity;
        this.isGrid = isGrid;
    }


    @Override
    public LauncherViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int position) {
        Log.i("LauncherActivity","Grid Launcher onCreateViewHolder");
        if(isGrid) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_icon_view, parent, false);

            return new LauncherViewHolder(view, R.id.grid_app_icon, R.id.grid_app_title);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_linear_view,parent,false);

            return new LauncherViewHolder(view,R.id.linear_app_icon,R.id.linear_app_title);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull LauncherViewHolder viewHolder, int position) {
        Log.i("LauncherActivity","Grid Launcher onBindViewHolder");
        ResolveInfo resolveInfo = mInstalledAppInfo.get(position);
        PackageManager pm = this.activity.getPackageManager();

        if(mIcons.get(resolveInfo) == null){
            mIcons.put(resolveInfo,mInstalledAppInfo.get(position).loadIcon(pm));
        }

        viewHolder.bind(resolveInfo,mIcons.get(resolveInfo));
    }

    public List<ResolveInfo> getmInstalledAppInfo() {
        return mInstalledAppInfo;
    }

    @Override
    public int getItemCount() {
        return mInstalledAppInfo.size();
    }
}
