package com.kawsarkobir2018.ipl;

import java.io.Serializable;

/**
 * Created by HP-NPC on 17/11/2017.
 */

public class PaymentHistory implements Serializable{
    public String paymentMethod;
    public String paymentAmount;
    public String paymentInput;

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

    public int getPaymentProof() {
        return paymentProof;
    }

    public void setPaymentProof(int paymentProof) {
        this.paymentProof = paymentProof;
    }

    public String paymentRoyal;
    public String paymentDate;
    public int paymentProof;

    public PaymentHistory() {

    }

    public PaymentHistory(String paymentMethod, String paymentAmount, String paymentInput, String paymentRoyal, String paymentDate, int paymentProof) {
        this.paymentMethod = paymentMethod;
        this.paymentAmount = paymentAmount;
        this.paymentInput = paymentInput;
        this.paymentRoyal = paymentRoyal;
        this.paymentDate = paymentDate;
        this.paymentProof = paymentProof;
    }
}
