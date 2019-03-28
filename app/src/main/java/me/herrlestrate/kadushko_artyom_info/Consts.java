package me.herrlestrate.kadushko_artyom_info;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import com.yandex.metrica.YandexMetrica;

import me.herrlestrate.kadushko_artyom_info.fragments.SQLLiteWorker;

public class Consts {
    private static int lastFragment = R.id.desktop_list;
    private static PagerAdapter DesktopPagerAdapter;

    private static SQLLiteWorker SQL = null;
    private static Activity activity;
    private static View desktopView;
    private static LayoutInflater desktopInflater;

    public static void initSQL(Context context){
        if(SQL != null)return;
        YandexMetrica.reportEvent("SQLWorker init!");
        SQL = new SQLLiteWorker(context);
    }

    public static void add(String name){
        SQL.add(name,1);
    }

    public static int get(String name){
        //System.out.println("CAST!");
        return SQL.get(name);
    }

    public static String getByPos(int i,int j){
        return SQL.getByPos(i,j);
    }

    public static void setAppLocation(String name,int i,int j){
        SQL.setAppLocation(name,i,j);
    }

    public static void removeByPos(int i,int j){
        if(!SQL.deleteByPos(i,j)) System.out.println("Not deleted");
    }

    public static int getLastFragment() {
        return lastFragment;
    }

    public static void setLastFragment(int value){
        lastFragment = value;
    }

    public static PagerAdapter getDesktopPagerAdapter() {
        return DesktopPagerAdapter;
    }

    public static void setDesktopPagerAdapter(PagerAdapter desktopPagerAdapter) {
        DesktopPagerAdapter = desktopPagerAdapter;
    }

    public static View getDesktopView() {
        return desktopView;
    }

    public static void setDesktopView(View desktopView) {
        Consts.desktopView = desktopView;
    }

    public static int getTheme(Activity activity){
        SharedPreferences sharedPreferences = activity
            .getSharedPreferences("me.herrlestrate.kadushko_artyom_info_preferences",0);
        if(!sharedPreferences.getBoolean("theme",false))return R.style.AppTheme;
            else return(R.style.AppThemeDark);
    }

    public static int getTheme(SharedPreferences sharedPreferences){
        if(!sharedPreferences.getBoolean("theme",false))return R.style.AppTheme;
        else return(R.style.AppThemeDark);
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        Consts.activity = activity;
    }

    public static LayoutInflater getDesktopInflater() {
        return desktopInflater;
    }

    public static void setDesktopInflater(LayoutInflater desktopInflater) {
        Consts.desktopInflater = desktopInflater;
    }
}
