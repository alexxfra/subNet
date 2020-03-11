package com.example.subnet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.subnet.address.Network;

public class SubnetMaskActivity extends AppCompatActivity {

    private static final String PREFNAME = "preferences";
    private static final String THEME = "dark_theme";

    private TextView textView, mask, decSpecs, decOut, binSpecs, binOut;
    private SeekBar seekBar;
    ActionBar actionBar;
    private Network n;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        if (sp.getBoolean(THEME,false))
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subnet_mask);



        n = new Network();

        textView = findViewById(R.id.networkPrefix);
        mask = findViewById(R.id.subnetMask);
        seekBar = findViewById(R.id.maskSeeker);

        decSpecs = findViewById(R.id.specs1);
        decOut = findViewById(R.id.decOutput);
        binSpecs = findViewById(R.id.specs2);
        binOut = findViewById(R.id.binOutput);

        seekBar.setProgress(24);
        seekBar.setMin(1);
        seekBar.setMax(31);

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Subnet Mask List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(getApplicationContext().getString(R.string.maskPrefix, Integer.toString(progress)));

                n.updateMask(progress);
                decSpecs.setVisibility(View.VISIBLE);
                binSpecs.setVisibility(View.VISIBLE);

                mask.setText(n.formatMaskToDecimal());
                decOut.setText(n.toMaskDecimal());
                binOut.setText(n.toMaskBinary());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.generalbar, menu);
        return true;
    }
}
