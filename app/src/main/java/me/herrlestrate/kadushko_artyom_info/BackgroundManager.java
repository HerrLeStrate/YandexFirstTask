package me.herrlestrate.kadushko_artyom_info;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.view.View;

import java.io.File;
import java.util.Calendar;

public class BackgroundManager {

    public BackgroundManager(){

    }

    public static void startJobService(final View view, final String path, final Long startDelay){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(startDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SharedPreferences preferences = view.getContext()
                        .getSharedPreferences("me.herrlestrate.kadushko_artyom_info_preferences", 0);

                ComponentName componentName = new ComponentName(view.getContext(),ImageService.class);
                PersistableBundle bundle = new PersistableBundle();

                bundle.putString("path",path);

                int type =  Integer.valueOf(preferences.getString("imageUpdateDelay","0"));
                long period = getTime(preferences,type);

                System.out.println();
                JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(1292,componentName)
                        .setPeriodic(period)
                        .setExtras(bundle);
                JobInfo jobInfo = jobInfoBuilder.build();


                JobScheduler jobScheduler = (JobScheduler) view.getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                jobScheduler.schedule(jobInfo);
                System.out.println("Pe3");
            }
        }).start();
    }

    public static void startJobService(View view, String path){
        File background = new File(path);
        long startDelay = getStartDelay(view);
        if(!background.exists()){
            startDelay = 0;
        }
        startJobService(view,path,startDelay);
    }

    private static long getTime(SharedPreferences preferences,int type){
        String result = "900000";
        switch (type){
            case 0:
                result = "900000";
                break;
            case 1:
                result = "3600000";
                break;
            case 2:
                result = "28800000";
                break;
            case 3:
                result = "86400000";
                break;
        }
        System.out.println("Period time is: "+result);
        return Long.parseLong(result);
    }

    private static long getStartDelay(View view){
        SharedPreferences preferences = view.getContext()
                .getSharedPreferences("me.herrlestrate.kadushko_artyom_info_preferences", 0);
        Calendar calendar = Calendar.getInstance();
        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int type = Integer.valueOf(preferences.getString("imageUpdateDelay","0"));

        long mLeft = 0;
        long hLeft = 0;

        switch (type){
            case 0:
                mLeft = 15 - (currentMinute % 15);
                break;
            case 1:
                mLeft = 60 - currentMinute;
                break;
            case 2:
                mLeft = 60 - currentMinute;
                hLeft = 7 - (currentHour % 8);
                break;
            case 3:
                mLeft = 60 - currentMinute;
                hLeft = 24 - currentHour;
                break;
        }

        return ((hLeft * 60L + mLeft) * 60L * 1000L);
    }

    private void updateImage(View view,String path){
    }
}
