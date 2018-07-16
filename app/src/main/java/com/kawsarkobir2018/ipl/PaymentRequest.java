package com.kawsarkobir2018.ipl;

import java.io.Serializable;

/**
 * Created by HP-NPC on 16/11/2017.
 */

public class PaymentRequest implements Serializable{
    public String paymentMethod;
    public String paymentAmount;
    public String paymentInput;
    public String paymentRoyal;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentInput() {
        return paymentInput;
    }

    public void setPaymentInput(String paymentInput) {
        this.paymentInput = paymentInput;
    }

    public String getPaymentRoyal() {
        return paymentRoyal;
    }

    public void setPaymentRoyal(String paymentRoyal) {
        this.paymentRoyal = paymentRoyal;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String paymentDate;
    public String userName;
    public String phone;

    public PaymentRequest() {

    }

    public PaymentRequest(String paymentMethod, String paymentAmount, String paymentInput, String paymentRoyal, String paymentDate, String userName,String phone) {
        this.paymentMethod = paymentMethod;
        this.paymentAmount = paymentAmount;
        this.paymentInput = paymentInput;
        this.paymentRoyal = paymentRoyal;
        this.paymentDate = paymentDate;
        this.userName = userName;
        this.phone = phone;
    }
}
