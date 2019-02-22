package me.herrlestrate.kadushko_artyom_info;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import me.herrlestrate.kadushko_artyom_info.fragments.launcher.LauncherViewHolder;

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
        } else if(intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)){
            Log.i("Notifer","Add Detected!");
        }
    }
}
