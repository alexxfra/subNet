package com.example.subnet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.subnet.address.Network;
import com.example.subnet.singleton.Loader;

/**
 * @author Xiao,Alex
 * @version 1.0
 *
 * Activity is used to display all available subnet masks.
 * It displays the prefix, subnet mask, wildcard mask, number of hosts.
 */
public class SubnetMaskActivity extends AppCompatActivity {
    //view variables
    private TextView prefixText, maskText, decimalSpecifications, decimalOutput, binarySpecifications, binaryOutput;
    private SeekBar seekBar;
    ActionBar actionBar;

    //Network used to display info
    private Network n = new Network();

    /**
     *  views and prefs as always, additionally we are setting up a listener for the seeker to control the changes of the prefix
     * @param savedInstanceState default parameters
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subnet_mask);

        prefixText = findViewById(R.id.networkPrefix);
        maskText = findViewById(R.id.subnetMask);
        seekBar = findViewById(R.id.maskSeeker);

        decimalSpecifications = findViewById(R.id.specs1);
        decimalOutput = findViewById(R.id.decOutput);
        binarySpecifications = findViewById(R.id.specs2);
        binaryOutput = findViewById(R.id.binOutput);

        seekBar.setProgress(24);
        seekBar.setMin(1);
        seekBar.setMax(31);

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Subnet Mask List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
         We are updating our network with a different subnet mask on every change.
         We use updateMask function and then get string iformation with toMask...
         */
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prefixText.setText(getApplicationContext().getString(R.string.maskPrefix, Integer.toString(progress)));

                n.updateMask(progress);
                decimalSpecifications.setVisibility(View.VISIBLE);
                binarySpecifications.setVisibility(View.VISIBLE);

                maskText.setText(n.formatMaskToDecimal());
                decimalOutput.setText(n.toMaskDecimal());
                binaryOutput.setText(n.toMaskBinary());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * inflates our menu
     * @param menu default parameter
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.generalbar, menu);
        return true;
    }

    public void updateTheme(){
        if (Loader.getInstance(this).getTheme())
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);
    }
}
