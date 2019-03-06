package me.herrlestrate.kadushko_artyom_info;

import android.content.Context;

import me.herrlestrate.kadushko_artyom_info.fragments.SQLLiteWorker;

public class Consts {
    private static int lastFragment = R.id.nav_grid;

    private static SQLLiteWorker SQL = null;

    public static void initSQL(Context context){
        if(SQL != null)return;
        SQL = new SQLLiteWorker(context);
    }

    public static void add(String name){
        SQL.add(name,1);
    }

    public static int get(String name){
        System.out.println("CAST!");
        return SQL.get(name);
    }

    public static int getLastFragment() {
        return lastFragment;
    }

    public static void setLastFragment(int value){
        lastFragment = value;
    }
}
