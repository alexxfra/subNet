package com.example.subnet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFNAME = "preferences";
    private static final String THEME = "dark_theme";


    private ActionBar actionBar;
    private Switch themeSwitch;
    private ConstraintLayout background;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        if (sp.getBoolean(THEME,false))
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Settings");

        themeSwitch = findViewById(R.id.change);
        themeSwitch.setChecked(sp.getBoolean(THEME,false));

        background = findViewById(R.id.view);

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    changeTheme(true);
                }
                else{
                    changeTheme(false);
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.generalbar, menu);
        return true;
    }

    public void changeTheme(boolean darkTheme){
        SharedPreferences.Editor editor = getSharedPreferences(PREFNAME,MODE_PRIVATE).edit();
        editor.putBoolean(THEME,darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }



}
