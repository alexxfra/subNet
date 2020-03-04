package com.example.subnet.address;

import android.util.Log;

import java.util.Random;

public class Network {

    private long hostInput;
    private long mask;
    private long wildcard;
    private long network;
    private long broadcast;
    private int prefix;
    private Random rn = new Random();

    public Network(){
        setNetwork(new long[] {192L,168L,10L,1L}, 24);
    }

    public Network(long[] input, int prefix){
        setNetwork(input, prefix);
    }

    public void setNetwork(long[] input, int prefix){
        this.prefix = prefix;

        /**
         * HOST IP
         */
        this.hostInput = ((input[0]<<24) + (input[1]<<16) + (input[2]<<8) + input[3]);

        /**
         * SUBNET MASK
         */
        String subnetMaskString = "";
        while(subnetMaskString.length() < prefix){
            subnetMaskString += "1";
        }
        this.mask = Long.parseLong(subnetMaskString,2)<<(32-prefix);

        /**
         * WILDCARD
         */
        subnetMaskString = "";
        while(subnetMaskString.length() < 32){
            if(subnetMaskString.length() < prefix)
                subnetMaskString += "0";
            else
                subnetMaskString += "1";
        }
        this.wildcard = Long.parseLong(subnetMaskString,2);

        /**
         * NETWORK
         */
        this.network = ((input[0]<<24) + (input[1]<<16) + (input[2]<<8) + input[3]) & this.mask;

        /**
         * BROADCAST
         */
        this.broadcast = this.network + this.wildcard;
    }

    public void updateMask(int prefix){
        this.prefix = prefix;

        String subnetMaskString = "";
        while(subnetMaskString.length() < prefix){
            subnetMaskString += "1";
        }
        this.mask = Long.parseLong(subnetMaskString,2)<<(32-prefix);

        subnetMaskString = "";
        while(subnetMaskString.length() < 32){
            if(subnetMaskString.length() < prefix)
                subnetMaskString += "0";
            else
                subnetMaskString += "1";
        }
        this.wildcard = Long.parseLong(subnetMaskString,2);

        this.network = this.hostInput & this.mask;

        this.broadcast = this.network + this.wildcard;

    }

    public String toBits(){
        return  formatIpBits(this.network) + "\n" +
                formatIpBits(this.mask) + "\n" +
                formatIpBits(this.network+1) + "\n" +
                formatIpBits(this.broadcast-1) + "\n" +
                formatIpBits(this.broadcast);
    }

    public String toDecimals(){
        return  formatIpDecimals(this.network) + "\n" +
                formatIpDecimals(this.mask) + "\n" +
                formatIpDecimals(this.network+1) + "\n" +
                formatIpDecimals(this.broadcast-1) + "\n" +
                formatIpDecimals(this.broadcast);
    }

    public String formatIpDecimals(long ipInput){
        StringBuilder ipOutput = new StringBuilder();
        ipOutput.append((ipInput>>24) + ".");
        ipOutput.append(((ipInput>>16) & 255) + ".");
        ipOutput.append(((ipInput>>8) & 255) +".");
        ipOutput.append(ipInput & 255);
        return ipOutput.toString();
    }

    public String formatIpBits(long ipInput){
        StringBuilder ipOutput = new StringBuilder();
        ipOutput.append(Long.toString(ipInput,2));

        while(ipOutput.toString().length() < 32) {
            ipOutput.insert(0,"0");
        }
        ipOutput.insert(8,".");
        ipOutput.insert(17,".");
        ipOutput.insert(26,".");
        return ipOutput.toString();
    }

    public String getHostCount(){
        return Integer.toString((int) Math.pow(2,32-this.prefix)-2);
    }

    public String getMaskInfo(){
        return  formatIpDecimals(this.mask) + "\n" +
                formatIpDecimals(this.wildcard) + "\n" +
                getHostCount();
    }

    public String getBitMaskInfo(){
        return  formatIpBits(this.mask) + "\n" +
                formatIpBits(this.wildcard);
    }



}
