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

    private static final String PREFNAME = "preferences";
    private static final String THEME = "dark_theme";
    private static final String TAG = "VlsmActivity";

    private ArrayList<Integer> test = new ArrayList<>();

    private ArrayList<Network> networks = new ArrayList<>();
    private Vlsm vlsm = new Vlsm();

    private RecyclerView rv;
    private RecyclerAdapter rva;

    //private RecyclerView rv;
    //private RecyclerAdapter ra;

    private Button btn;
    private EditText oct1, oct2, oct3, oct4, inpPrefix, subnetCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        if (sp.getBoolean(THEME,false))
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlsm);

        oct1 = findViewById(R.id.vlsmOctet1);
        oct2 = findViewById(R.id.vlsmOctet2);
        oct3 = findViewById(R.id.vlsmOctet3);
        oct4 = findViewById(R.id.vlsmOctet4);
        inpPrefix = findViewById(R.id.vlsmPrefix);
        subnetCount = findViewById(R.id.subnetCount);


        subnetCount = findViewById(R.id.subnetCount);
        btn = findViewById(R.id.vlsmButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean clearedChecks = true;
                int prefix = 0;
                int subnetNumber = 0;
                long[] address = new long[4];
                String strAddress = oct1.getText().toString() + " " + oct2.getText().toString() + " " + oct3.getText().toString() + " " + oct4.getText().toString();

                try {
                    prefix = Integer.parseInt(inpPrefix.getText().toString());
                    address = inputToLongArray(strAddress);
                    subnetNumber = Integer.parseInt(subnetCount.getText().toString());
                }
                catch (Exception e){
                    Log.d("EXCEPTION", "onClick: ZLE UDAJE KOKOT");
                    clearedChecks = false;
                    Toast.makeText(getBaseContext(), "Fields can not be empty", Toast.LENGTH_SHORT).show();
                }

                if (clearedChecks && (prefix > 0 && prefix < 32) && ((address[0] > -1 && address[0] < 256) && (address[1] > -1 && address[1] < 256) && (address[2] > -1 && address[2] < 256) && (address[3] > -1 && address[3] < 256))){
                    //Bundle
                    if(subnetNumber <= Math.pow(2,32-prefix)/4) {
                        HostDialog dialog = new HostDialog();
                        Bundle args = new Bundle();
                        args.putInt("num", subnetNumber);
                        dialog.setArguments(args);
                        dialog.show(getSupportFragmentManager(), "NetworkTry");
                    }
                    else{
                        Toast.makeText(getBaseContext(), "Number of networks is too large", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getBaseContext(),"Incorrect format", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeRec() {
        RecyclerView rv = findViewById(R.id.recycler);
        RecyclerAdapter rva = new RecyclerAdapter(this, this.networks);
        rv.setAdapter(rva);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    public long[] inputToLongArray(String input){
        String[] strInput = input.split(" ");

        long[] LInput = new long[4];

        for(int i = 0; i < 4; i++){
            LInput[i] = Long.parseLong(strInput[i]);
        }
        return LInput;
    }

    @Override
    public void applyHosts(ArrayList<Integer> networkSize){
        int prefix = Integer.parseInt(inpPrefix.getText().toString());
        String[] strArr = new String[]{oct1.getText().toString(), oct2.getText().toString(), oct3.getText().toString(), oct4.getText().toString()};
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

}



































