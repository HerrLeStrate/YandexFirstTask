package me.herrlestrate.kadushko_artyom_info;

public class Consts {
    private static int lastFragment = R.id.nav_grid;

    public static int getLastFragment() {
        return lastFragment;
    }

    public static void setLastFragment(int value){
        lastFragment = value;
    }
}
