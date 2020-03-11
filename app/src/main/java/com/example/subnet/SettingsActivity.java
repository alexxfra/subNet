package com.example.subnet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.subnet.singleton.Loader;

/**
 * @author Xiao,Alex
 * @version 1.0
 *
 * Settings activity is used to manage the theme of our app
 */
public class SettingsActivity extends AppCompatActivity {
    //view variables
    private ActionBar actionBar;
    private Switch themeSwitch;

    /**
     * again we are instatiating our views and loading shared preferences
     * additionally we are setting up a switch listener that controls the theme
     * @param savedInstanceState default parameter
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Settings");

        themeSwitch = findViewById(R.id.change);

        themeSwitch.setChecked(Loader.getInstance(this).getTheme());

        /*
        Depending on the switch it calls the changeTheme function
         */
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    changeTheme(true);
                else
                    changeTheme(false);

            }
        });
    }

    /**
     * inflates the menu
     * @param menu default parameter
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.generalbar, menu);
        return true;
    }

    /**
     * changes the theme depending on the boolean input
     * uses shared preferences to save the data
     * @param darkTheme true/false for dark/light
     */
    public void changeTheme(boolean darkTheme){
        Loader.getInstance(this).saveTheme(darkTheme);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void updateTheme(){
        if (Loader.getInstance(this).getTheme())
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);
    }

}
