package com.example.subnet;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

/**
 * @author Alex,Daniel
 * @version 1.0
 */
public class NetworkActivity extends AppCompatActivity {
    //pref variables
    private static final String PREFNAME = "preferences";
    private static final String THEME = "dark_theme";

    //view variables
    private ActionBar subnetBar;
    private Button calculate;
    private TextView decimalSpecifications, binarySpecifications, decimalOutput, binaryOutput;
    private EditText octet1, octet2, octet3, octet4, pref;

    // Making an instance of Network for subnetting capabilities
    private Network n = new Network();

    /**
     * Loading our Theme and instantiating views.
     * @param savedInstanceState default parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        if (sp.getBoolean(THEME,false))
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subnet_calc);

         decimalSpecifications = findViewById(R.id.networkDecSpec);
         binarySpecifications = findViewById(R.id.networkBinSpec);
         decimalOutput = findViewById(R.id.networkDecOut);
         binaryOutput = findViewById(R.id.networkBinOut);

         octet1 = findViewById(R.id.networkOct1);
         octet2 = findViewById(R.id.octet2);
         octet3 = findViewById(R.id.octet3);
         octet4 = findViewById(R.id.octet4);
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
                String strAddress = octet1.getText().toString() + " " + octet2.getText().toString() + " " + octet3.getText().toString() + " " + octet4.getText().toString();

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
                    decimalSpecifications.setVisibility(View.VISIBLE);
                    binarySpecifications.setVisibility(View.VISIBLE);

                    n.setNetwork(address,prefix);
                    decimalOutput.setText(n.toDecimals());
                    binaryOutput.setText(n.toBits());
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
