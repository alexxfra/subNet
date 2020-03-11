package com.example.subnet.address;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Alex
 * @version 1.0
 *
 * Vlsm uses networks to form a Subnet configuration consisting of more subnetworks.
 * It utilizes a list of networks which are taking the role of a subnetwork.
 */
public class Vlsm {
    //log tag
    private static final String TAG = "Vlsm";

    //variables
    private ArrayList<Network> networks;

    /**
     * Basic constructor. When creating an instance of Vlsm we only need to instantiate our Arraylist and Network
     * We use Network to acces it's functions
     */
    public Vlsm(){
        networks = new ArrayList<>();
    }

    /**
     * This function populates our arraylist with subnetworks of a network that the user specified.
     * @param network network ipv4 address
     * @param hosts array of numbers of hosts in a network
     */
    public void makeSubnets(long network, ArrayList<Integer> hosts){

        networks.clear();

        Log.d(TAG, "makeSubnets: SORTING");
        sortHosts(hosts);

        Log.d(TAG, "makeSubnets: ADDING FIRST");
        networks.add(new Network(network, prefixFromHosts(hosts.get(0))));

        if (hosts.size() > 1)
            for (int i = 1; i < hosts.size(); i++){
                Log.d(TAG, "makeSubnets: ADDING NUNBER " + i+1);
                networks.add(new Network(networks.get(i-1).getBroadcast()+1, prefixFromHosts(hosts.get(i))));
            }
    }

    /**
     * This function returns the lowest possible prefix for the number of hosts provided
     * @param hosts number of hosts
     * @return lowes prefix possible for those hosts
     */
    public int prefixFromHosts(int hosts){
        for(int i = 1; i < 32; i++){
            if(Math.pow(2,i) -2  >= hosts){
                return 32- i;
            }
        }
        return 0;
    }

    /**
     * this functions sorts the hosts array list in the descending order
     * @param hosts array list of hosts
     * @return array list sorted from highest to lowest
     */
    public ArrayList<Integer> sortHosts(ArrayList<Integer> hosts){

        Collections.sort(hosts,Collections.<Integer>reverseOrder());

        return hosts;
    }

    /**
     * getter for our arraylist
     * @return arraylist of networks
     */
    public ArrayList<Network> getNetworks() {
        return networks;
    }
}
