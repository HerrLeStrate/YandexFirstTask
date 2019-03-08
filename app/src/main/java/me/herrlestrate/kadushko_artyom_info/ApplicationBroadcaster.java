package me.herrlestrate.kadushko_artyom_info;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import me.herrlestrate.kadushko_artyom_info.fragments.launcher.LauncherViewHolder;
import me.herrlestrate.kadushko_artyom_info.fragments.launcher.MyRecyclerViewAdapter;

public class ApplicationBroadcaster extends BroadcastReceiver {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private Activity activity;

    public ApplicationBroadcaster(Context context, RecyclerView recyclerView){
        mContext=context;
        mRecyclerView=recyclerView;
    }

    public ApplicationBroadcaster(Activity activity){
        this.activity = activity;
    }

    public ApplicationBroadcaster(){
    }

    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)){
            Log.i("Notifer","Delete Detected!");
            MyRecyclerViewAdapter adapter = (MyRecyclerViewAdapter)mRecyclerView.getAdapter();
            int uid = intent.getIntExtra(Intent.EXTRA_UID,-1);
            if(uid==-1)return;
            ResolveInfo target = null;
            for(ResolveInfo resolveInfo : adapter.getmInstalledAppInfo()){
                if(resolveInfo.activityInfo.applicationInfo.uid == uid){
                    target = resolveInfo;
                }
            }
            if(target == null)return;
            adapter.getmInstalledAppInfo().remove(target);
            adapter.notifyDataSetChanged();
        } else if(intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)){
            Log.i("Notifer","Add Detected!");
            MyRecyclerViewAdapter adapter = (MyRecyclerViewAdapter)mRecyclerView.getAdapter();
            int uid = intent.getIntExtra(Intent.EXTRA_UID,-1);
            if(uid==-1)return;
            ResolveInfo target = null;
            for(ResolveInfo resolveInfo : adapter.getmInstalledAppInfo()){
                if(resolveInfo.activityInfo.applicationInfo.uid == uid){
                    target = resolveInfo;
                }
            }
            if(target == null)return;
            adapter.getmInstalledAppInfo().remove(target);
            adapter.notifyDataSetChanged();
        }
    }
}
