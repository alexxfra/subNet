package com.example.subnet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.subnet.address.Network;
import com.example.subnet.address.Vlsm;
import com.example.subnet.dialog.HostDialog;

import java.util.ArrayList;

public class VlsmActivity extends AppCompatActivity implements HostDialog.dialogListener{

    //log tag
    private static final String TAG = "VlsmActivity";

    //pref variables
    private static final String PREFNAME = "preferences";
    private static final String THEME = "dark_theme";

    //view variables
    private Button btn;
    private EditText octet1, octet2, octet3, octet4, prefix, subnetCount;

    //defining vlsm, network, and instantiating our ArrayList
    private ArrayList<Network> networks = new ArrayList<>();
    private Vlsm vlsm = new Vlsm();
    private Network net = new Network();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        if (sp.getBoolean(THEME,false))
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlsm);

        octet1 = findViewById(R.id.vlsmOctet1);
        octet2 = findViewById(R.id.vlsmOctet2);
        octet3 = findViewById(R.id.vlsmOctet3);
        octet4 = findViewById(R.id.vlsmOctet4);
        prefix = findViewById(R.id.vlsmPrefix);
        subnetCount = findViewById(R.id.subnetCount);
        btn = findViewById(R.id.vlsmButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()){
                    int subnetNumber = Integer.parseInt(subnetCount.getText().toString());

                    HostDialog dialog = new HostDialog();
                    Bundle args = new Bundle();
                    args.putInt("num", subnetNumber);
                    dialog.setArguments(args);
                    dialog.show(getSupportFragmentManager(), "NetworkTry");
                }
            }
        });
    }

    private void initializeRec() {
        RecyclerView rv = findViewById(R.id.recycler);
        RecyclerAdapter rva = new RecyclerAdapter(this.networks);
        rv.setAdapter(rva);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void applyHosts(ArrayList<Integer> networkSize){
        int prefix = Integer.parseInt(this.prefix.getText().toString());
        String[] strArr = new String[]{octet1.getText().toString(), octet2.getText().toString(), octet3.getText().toString(), octet4.getText().toString()};
        long[] longInput= new long[]{Long.parseLong(strArr[0]), Long.parseLong(strArr[1]), Long.parseLong(strArr[2]), Long.parseLong(strArr[3])};
        long longNetwork = longArrToLong(longInput) & vlsm.maskFromPrefix(prefix);

        int sum = 0;

        int[] hosts = new int[networkSize.size()];
        for(int i = 0; i < networkSize.size(); i++){
            hosts[i] = networkSize.get(i);
            sum += Math.pow(2,32-vlsm.prefixFromHosts(hosts[i]))-2;
            Log.d(TAG, "applyHosts: HOST" + i);
        }

        if(sum <= Math.pow(2,32-prefix)-2*hosts.length){
            vlsm.makeSubnets(longNetwork, hosts);
            networks = (ArrayList<Network>) vlsm.getNetworks().clone();
            initializeRec();
        }
        else
            Toast.makeText(getBaseContext(),"Too many hosts for defined network size", Toast.LENGTH_LONG).show();
    }

    public long longArrToLong(long[] input){
        return ((input[0]<<24) + (input[1]<<16) + (input[2]<<8) + input[3]);
    }

    /**
     * This function validates the input of the ip address.
     * If the input is not empty, the numbers are in the correct range (8bit number and prefix from 1-31) and the amount of subnets is correct it returns true otherwise false.
     * @return true or false depending on vaidation success
     */
    public boolean validateInput(){
        //This try/catch will thrown an exception if input is empty.
        try {
            /*
            0-3 -> octet 1-4
            4 -> prefix
            5 -> subnetCount
             */
            String[] userInput = {octet1.getText().toString(),octet2.getText().toString(),octet3.getText().toString(),octet4.getText().toString(),prefix.getText().toString(),subnetCount.getText().toString()};
            long[] longUserInput = {Long.parseLong(userInput[0]),Long.parseLong(userInput[1]),Long.parseLong(userInput[2]),Long.parseLong(userInput[3]),Long.parseLong(userInput[4]),Long.parseLong(userInput[5])};

            //long if to check if every field is empty has the correct format.
            if((longUserInput[0] > 0 && longUserInput[0] < 256) && (longUserInput[1] > 0 && longUserInput[1] < 256) && (longUserInput[2] > 0 && longUserInput[2] < 256) && (longUserInput[3] > 0 && longUserInput[3] < 256) && (longUserInput[4] > 0 && longUserInput[4] < 32) && (longUserInput[5] <= Math.pow(2,32-longUserInput[4])/4)){
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



































