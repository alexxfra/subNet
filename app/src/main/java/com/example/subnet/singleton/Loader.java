package com.example.subnet.singleton;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Alex
 * @version 1.0
 *
 * This singleton is used to load and save the theme of the app in all activities.
 */
public class Loader {
    private static final String PREFNAME = "preferences";
    private static final String THEME = "dark_theme";

    private static Loader loader;
    private SharedPreferences  sPref;
    private SharedPreferences.Editor editor;

    private  Loader(Context context){
        sPref = context.getSharedPreferences(PREFNAME,Context.MODE_PRIVATE);
    }

    public static Loader getInstance(Context context){
        if (loader == null){
            loader = new Loader(context.getApplicationContext());
        }
        return loader;
    }

    public void saveTheme(boolean themeBool){
        editor = sPref.edit();
        editor.putBoolean(THEME, themeBool);
        editor.commit();
        editor = null;
    }

    public boolean getTheme(){
        return sPref.getBoolean(THEME,true);
    }
}
