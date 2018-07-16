package com.kawsarkobir2018.ipl;

import java.util.ArrayList;

/**
 * Created by HP-NPC on 14/11/2017.
 */

public class UserInfo {
    public String name;
    public String email;
    public Integer point;
    public Integer totalPoint;
    public Integer refPoint;
    public Integer wrongAttempt;
    public boolean isEverRef;
    public ArrayList<String> refedPersons = new ArrayList<>();
    public ArrayList<Integer> viewdApps = new ArrayList<>();
    public ArrayList<PaymentHistory> paymentHistory = new ArrayList<>();
    public String phone,pass;

    public UserInfo(){

    }

    public UserInfo(String name, String email, Integer point,Integer totalPoint, Integer refPoint, boolean isEverRef, ArrayList<String> refedPersons,ArrayList<Integer> viewdApps,ArrayList<PaymentHistory> paymentHistory,String phone,String password,Integer wrongAttempt) {
        this.name = name;
        this.email = email;
        this.point = point;
        this.refPoint = refPoint;
        this.isEverRef = isEverRef;
        this.refedPersons = refedPersons;
        this.viewdApps = viewdApps;
        this.paymentHistory = paymentHistory;
        this.phone = phone;
        this.pass = password;
        this.totalPoint = totalPoint;
        this.wrongAttempt = wrongAttempt;
    }
}
