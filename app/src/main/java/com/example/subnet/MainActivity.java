package com.example.subnet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String PREFNAME = "preferences";
    private static final String THEME = "dark_theme";

    private ActionBar homeBar;
    private Button one,two,three,four,five;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        if (sp.getBoolean(THEME,false))
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeBar = getSupportActionBar();
        homeBar.setTitle("Home");

        one = findViewById(R.id.buttonSubnetCalc);
        one.setOnClickListener(this);

        two = findViewById(R.id.buttonVlsmCalc);
        two.setOnClickListener(this);

        three = findViewById(R.id.buttonMaskConv);
        three.setOnClickListener(this);

        four = findViewById(R.id.buttonSettings);
        four.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miAbout:
                Dialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("This app is meant for network Administrators\nAs well as network" +
                " e-word\nMakes it easier to calculate the Subnet\nAnd save it");
        builder.setTitle("About");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            { dialog.cancel(); }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        TextView tv = findViewById(R.id.textViewTitle);
        switch (v.getId()) {
            case R.id.buttonSubnetCalc:
               Intent subnetCalc = new Intent(this, SubnetCalcActivity.class);
               startActivity(subnetCalc);
               break;
            case R.id.buttonVlsmCalc:
                // Intent vlsmCalc = new Intent(this, vlsmCalc.class);
                //startActivity(vlsmCalc);
                break;
            case R.id.buttonMaskConv:
                Intent maskConv = new Intent(this, SubnetMaskActivity.class);
                startActivity(maskConv);
                break;

            case R.id.buttonSettings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                break;

            default:
                break;
        }
    }
}
