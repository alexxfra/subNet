package com.example.subnet.singleton;

import android.content.SharedPreferences;


public class Loader {
    public static Loader loader;

    private static final String PREFNAME = "preferences";
    private static final String THEME = "dark_theme";
    private SharedPreferences a;

    private Loader(){

    }

    public static Loader getInstance(){
        if (loader == null){
            loader = new Loader();
        }
        return loader;
    }

    public void loadTheme(){

    }

}
