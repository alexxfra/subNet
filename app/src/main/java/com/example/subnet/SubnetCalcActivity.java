package com.example.subnet;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subnet.address.Network;

public class SubnetCalcActivity extends AppCompatActivity {

    private static final String PREFNAME = "preferences";
    private static final String THEME = "dark_theme";

    private ActionBar subnetBar;
    private Button calculate;
    private TextView specs1, specs2, decOut, bitOut;
    private EditText oct1, oct2, oct3, oct4, pref;
    private Network n = new Network();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        if (sp.getBoolean(THEME,false))
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subnet_calc);

         specs1 = findViewById(R.id.ipSpecs1);
         specs2 = findViewById(R.id.ipSpecs2);
         decOut = findViewById(R.id.recItemOutput);
         bitOut = findViewById(R.id.bitOutput);

         oct1 = findViewById(R.id.octet1);
         oct2 = findViewById(R.id.octet2);
         oct3 = findViewById(R.id.octet3);
         oct4 = findViewById(R.id.octet4);
         pref = findViewById(R.id.prefix);


        subnetBar = getSupportActionBar();
        assert subnetBar != null;
        subnetBar.setTitle("Subnet Calculator");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calculate = findViewById(R.id.subnetCalculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean clearedChecks = true;
                int prefix = 0;
                long[] address = new long[4];
                String strAddress = oct1.getText().toString() + " " + oct2.getText().toString() + " " + oct3.getText().toString() + " " + oct4.getText().toString();

                try {
                    prefix = Integer.parseInt(pref.getText().toString());
                    address = inputToLongArray(strAddress);
                }
                catch (Exception e){
                    Log.d("EXCEPTION", "onClick: ZLE UDAJE KOKOT");
                    clearedChecks = false;
                    Toast.makeText(getBaseContext(),"Fields can not be empty", Toast.LENGTH_SHORT).show();

                }


                if (clearedChecks && (prefix > 0 && prefix < 32) && ((address[0] > -1 && address[0] < 256) && (address[1] > -1 && address[1] < 256) && (address[2] > -1 && address[2] < 256) && (address[3] > -1 && address[3] < 256))){
                    specs1.setVisibility(View.VISIBLE);
                    specs2.setVisibility(View.VISIBLE);

                    n.setNetwork(address,prefix);
                    decOut.setText(n.toDecimals());
                    bitOut.setText(n.toBits());
                }
                else{
                    Toast.makeText(getBaseContext(),"Incorrect format", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.generalbar, menu);
        return true;
    }

    public long[] inputToLongArray(String input){
        String[] strInput = input.split(" ");

        long[] LInput = new long[4];

        for(int i = 0; i < 4; i++){
            LInput[i] = Long.parseLong(strInput[i]);
        }
        return LInput;
    }
}
