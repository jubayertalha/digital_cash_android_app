package com.kawsarkobir2018.ipl;

import java.util.ArrayList;

/**
 * Created by HP-NPC on 23/11/2017.
 */

public class IpClass {
    public ArrayList<String> ip = new ArrayList<String>();

    public IpClass() {

    }

    public IpClass(ArrayList<String> ip) {
        this.ip = ip;
    }

    public ArrayList<String> getIp() {
        return ip;
    }

    public void setIp(ArrayList<String> ip) {
        this.ip = ip;
    }
}
