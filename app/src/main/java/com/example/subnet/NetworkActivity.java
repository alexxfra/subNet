package com.example.subnet;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
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
    private EditText octet1, octet2, octet3, octet4, prefix;

    // Making an instance of Network for subnetting capabilities
    private Network n = new Network();

    //log TAG
    private static final String TAG = "NetworkActivity";

    /**
     * Loading our Theme and instantiating views.
     * We are alsto setting an onclick listener to generate a config after the user clicks a button.
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
        setContentView(R.layout.activity_network);

         decimalSpecifications = findViewById(R.id.networkDecSpec);
         binarySpecifications = findViewById(R.id.networkBinSpec);
         decimalOutput = findViewById(R.id.networkDecOut);
         binaryOutput = findViewById(R.id.networkBinOut);
         calculate = findViewById(R.id.subnetCalculate);

         octet1 = findViewById(R.id.networkOct1);
         octet2 = findViewById(R.id.networkOct2);
         octet3 = findViewById(R.id.networkOct3);
         octet4 = findViewById(R.id.networkOct4);
         prefix = findViewById(R.id.networkPrefix);


        subnetBar = getSupportActionBar();
        assert subnetBar != null;
        subnetBar.setTitle("Subnet Calculator");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                If the input is correct we will make the specifications visible and we will create the network according to those specifications.
                Afterwards the info of the network is displayed in a textview
                 */
                if (validateInput()){
                    decimalSpecifications.setVisibility(View.VISIBLE);
                    binarySpecifications.setVisibility(View.VISIBLE);

                    long addressIn = n.stringToLong(octet1.getText().toString() + "." + octet2.getText().toString() + "." + octet3.getText().toString() + "." + octet4.getText().toString());
                    int prefixIn = Integer.parseInt(prefix.getText().toString());
                    n.setNetwork(addressIn,prefixIn);
                    decimalOutput.setText(n.toNetworkDecimal());
                    binaryOutput.setText(n.toNetworkBinary());
                }
            }
        });
    }

    /**
     * Inflates the menu
     * @param menu default parameter
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.generalbar, menu);
        return true;
    }

    /**
     * This function validates the input of the ip address.
     * If the input is not empty and the numbers are in the correct range (8bit number and prefix from 1-31) returns true otherwise false.
     * @return true or false depending on vaidation success
     */
    public boolean validateInput(){
        //This try/catch will thrown an exception if input is empty.
        try {
            String[] userInput = {octet1.getText().toString(),octet2.getText().toString(),octet3.getText().toString(),octet4.getText().toString(),prefix.getText().toString()};
            long[] longUserInput = {Long.parseLong(userInput[0]),Long.parseLong(userInput[1]),Long.parseLong(userInput[2]),Long.parseLong(userInput[3]),Long.parseLong(userInput[4])};

            //long if to check if every field is empty has the correct format.
            if((longUserInput[0] > 0 && longUserInput[0] < 256) && (longUserInput[1] > 0 && longUserInput[1] < 256) && (longUserInput[2] > 0 && longUserInput[2] < 256) && (longUserInput[3] > 0 && longUserInput[3] < 256) && (longUserInput[4] > 0 && longUserInput[4] < 32)){
               return true;
            }
            else{
                Toast.makeText(getBaseContext(),"Incorrect format", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),"Fields can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
    }



}
