package com.example.subnet.address;

import java.util.Random;

/**
 * @author Alex
 * @version 1.0
 */
public class Network {
    //
    private long hostInput;
    private long mask;
    private long wildcard;
    private long network;
    private long broadcast;
    private int prefix;

    public Network(){
        setNetwork(new long[] {192L,168L,10L,1L}, 24);
    }

    public Network(long input, int prefix){
        this.prefix = prefix;

        this.hostInput = input;

        StringBuilder sb = new StringBuilder();
        while(sb.length() < prefix){
            sb.append("1");
        }
        this.mask = Long.parseLong(sb.toString(),2)<<(32-prefix);

        sb.delete(0, sb.length());
        while(sb.length() < 32){
            if(sb.length() < prefix)
                sb.append("0");
            else
                sb.append("1");
        }
        this.wildcard = Long.parseLong(sb.toString(),2);

        this.network = this.hostInput & this.mask;

        this.broadcast = this.hostInput | this.wildcard;
    }

    public void setNetwork(long[] input, int prefix){
        this.prefix = prefix;

        this.hostInput = ((input[0]<<24) + (input[1]<<16) + (input[2]<<8) + input[3]);

        StringBuilder sb = new StringBuilder();
        while(sb.length() < prefix){
            sb.append("1");
        }
        this.mask = Long.parseLong(sb.toString(),2)<<(32-prefix);

        sb.delete(0, sb.length());
        while(sb.length() < 32){
            if(sb.length() < prefix)
                sb.append("0");
            else
                sb.append("1");
        }
        this.wildcard = Long.parseLong(sb.toString(),2);

        this.network = this.hostInput & this.mask;

        this.broadcast = this.hostInput | this.wildcard;
    }

    public void updateMask(int prefix){
        this.prefix = prefix;

        StringBuilder sb = new StringBuilder();
        while(sb.length() < prefix){
            sb.append("1");
        }
        this.mask = Long.parseLong(sb.toString(),2)<<(32-prefix);

        sb.delete(0, sb.length());
        while(sb.length() < 32){
            if(sb.length() < prefix)
                sb.append("0");
            else
                sb.append("1");
        }
        this.wildcard = Long.parseLong(sb.toString(),2);

        this.network = this.hostInput & this.mask;

        this.broadcast = this.network + this.wildcard;

    }


    /**
     * Returns a decimal string which is then displayed in NetworkActivity
     * @return Decimal format of network specifications
     */
    public String toNetworkDecimal(){
        return  formatLongToDecimal(this.network) + "\n" +
                formatLongToDecimal(this.mask) + "\n" +
                formatLongToDecimal(this.network+1) + "\n" +
                formatLongToDecimal(this.broadcast-1) + "\n" +
                formatLongToDecimal(this.broadcast);
    }

    /**
     * Returns a binary string which is then displayed in NetworkActivity
     * @return Binary format of network specifications
     */
    public String toNetworkBinary(){
        return  formatLongToBinary(this.network) + "\n" +
                formatLongToBinary(this.mask) + "\n" +
                formatLongToBinary(this.network+1) + "\n" +
                formatLongToBinary(this.broadcast-1) + "\n" +
                formatLongToBinary(this.broadcast);
    }

    /**
     * Returns a decimal string which is displayed in SubnetMaskActivity
     * @return Decimal format of subnet mask specifications
     */
    public String toMaskDecimal(){
        return  formatLongToDecimal(this.mask) + "\n" +
                formatLongToDecimal(this.wildcard) + "\n" +
                getHostCount();
    }

    /**
     * Returns a binary string which is displayed in SubnetMaskActivity
     * @return Binary format of subnet mask specifications
     */
    public String toMaskBinary(){
        return  formatLongToBinary(this.mask) + "\n" +
                formatLongToBinary(this.wildcard);
    }

    /**
     * Returns a string which is displayed in VlsmActivity
     * @return Decimal format containing shorter info of a subnetwork
     */
    public String toVlsm(){
        return  formatLongToDecimal(this.network) + "\n" +
                formatLongToDecimal(this.network+1) + " - " +
                formatLongToDecimal(this.broadcast-1) + "\n" +
                formatLongToDecimal(this.broadcast) + "\n" +
                (int)Math.pow(2,32-this.prefix);
    }

    /**
     * Formats the input address to decimal format
     * @param ipInput ip address in long format
     * @return decimal string representation of the ip
     */
    public String formatLongToDecimal(long ipInput){
        StringBuilder ipOutput = new StringBuilder();
        ipOutput.append((ipInput>>24)).append(".");
        ipOutput.append((ipInput>>16) & 255).append(".");
        ipOutput.append((ipInput>>8) & 255).append(".");
        ipOutput.append(ipInput & 255);
        return ipOutput.toString();
    }

    /**
     * Formats the input address to binary format
     * @param ipInput ip address in long format
     * @return binary string representation of the ip
     */
    public String formatLongToBinary(long ipInput){
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

    /**
     * Returns the number of hosts available in the network
     * @return number of available hosts
     */
    public int getHostCount(){
        return (int) Math.pow(2,32-this.prefix)-2;
    }

    /**
     *
     * @return decimal format
     */
    public String formatMaskToDecimal(){
        return formatLongToDecimal(this.mask);
    }

    /**
     * returns broadcast getter
     * @return long broadcast value
     */
    public long getBroadcast(){
        return this.broadcast;
    }


}
