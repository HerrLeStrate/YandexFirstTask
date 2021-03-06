package me.herrlestrate.kadushko_artyom_info.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLLiteWorker {
    private SQLiteDatabase db;

    public SQLLiteWorker(Context context){
        db = context.openOrCreateDatabase("app.db",Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS stats (app_name TEXT, number INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS app_location (package_name TEXT, x INTEGER, y INTEGER)");
        /*ContentValues contentValues = new ContentValues();
        contentValues.put("name","Artyom");
        contentValues.put("age","18");
        db.insert("users",null, contentValues);
        Cursor cursor = db.query("users",new String[] {"name","age"},null,null,null,null,null);
        System.out.println(cursor.getCount());*/
    }

    public void add(String name,int number){
        Cursor cursor = db.query("stats",new String[] {"app_name", "number"},"app_name = ?",new String[] {name},null,null,null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_name",name);
            contentValues.put("number",1);
            db.insert("stats",null,contentValues);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("number",1+cursor.getInt(1));
            db.update("stats",contentValues,"app_name = ?",new String[] {name});

        }

    }

    public int get(String name){
        Cursor cursor = db.query("stats",new String[] {"app_name", "number"},"app_name = ?",
                new String[] {name},null,null,null);
        System.out.println(name);
        cursor.moveToFirst();
        if(cursor.getCount() == 0)return 0;
        return cursor.getInt(1);
    }

    public String getByPos(int i,int j){
        Cursor cursor = db.query("app_location",new String[] {"package_name"},"x = ? AND y = ?",
                new String[] {String.valueOf(i),String.valueOf(j)},null,null,null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0)return "none";
        return cursor.getString(0);
    }

    public boolean deleteByPos(int i,int j){
        return db.delete("app_location","x = ? AND y = ?",new String[] {String.valueOf(i),String.valueOf(j)}) > 0;
    }

    public void setAppLocation(String name,int i,int j){
        Cursor cursor = db.query("app_location",new String[] {"package_name"},"package_name = ?",new String[] {name}
            ,null,null,null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("package_name", name);
            contentValues.put("x", i);
            contentValues.put("y", j);
            db.insert("app_location", null, contentValues);
        }
    }
}
