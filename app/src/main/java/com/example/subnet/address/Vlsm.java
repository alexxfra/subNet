package com.example.subnet.address;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Alex
 * @version 1.0
 *
 * Vlsm uses networks to form a Subnet configuration consisting of more subnetworks.
 * It utilizes a list of networks which are taking the role of a subnetwork.
 */
public class Vlsm {
    //pref variables
    private static final String TAG = "Vlsm";
    private ArrayList<Network> networks;
    private Network net;

    /**
     * Basic constructor. When creating an instance of Vlsm we only need to instantiate our Arraylist and Network
     * We use Network to acces it's functions
     */
    public Vlsm(){
        networks = new ArrayList<>();
        net = new Network();
    }

    /**
     *
     * @param network network ipv4 address
     * @param hosts array of numbers of hosts in a network
     */
    public void makeSubnets(long network, int[] hosts){

        networks.clear();

        Log.d(TAG, "makeSubnets: SORTING");
        sortHosts(hosts);

        Log.d(TAG, "makeSubnets: ADDING FIRST");
        networks.add(new Network(network, prefixFromHosts(hosts[0])));

        if (hosts.length > 1)
            for (int i = 1; i < hosts.length; i++){
                Log.d(TAG, "makeSubnets: ADDING NUNBER " + i+1);
                networks.add(new Network(networks.get(i-1).getBroadcast()+1, prefixFromHosts(hosts[i])));
            }
    }

    public int prefixFromHosts(int hosts){
        for(int i = 1; i < 32; i++){
            if(Math.pow(2,i) -2  >= hosts){
                return 32- i;
            }
        }
        return 0;
    }

    public long maskFromPrefix(int prefix){
        StringBuilder sb = new StringBuilder();
        while(sb.length() < prefix){
            sb.append("1");
        }
        return Long.parseLong(sb.toString(),2)<<(32-prefix);
    }

    public int[] sortHosts(int[] hosts){

        Arrays.sort(hosts);
        int tmp;
        for (int i = 0; i < hosts.length/2; i++){
            tmp = hosts[i];
            hosts[i] = hosts[hosts.length-1-i];
            hosts[hosts.length-1-i] = tmp;
        }

        return hosts;
    }

    public ArrayList<Network> getNetworks() {
        return networks;
    }

    @Override
    public String toString() {
        return networks.get(0).toNetworkDecimal();
    }
}
