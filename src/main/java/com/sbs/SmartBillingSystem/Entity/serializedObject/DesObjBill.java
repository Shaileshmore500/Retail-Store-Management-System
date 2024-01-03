package com.sbs.SmartBillingSystem.Entity.serializedObject;

import java.util.Date;

public class DesObjBill {
    private int bill_pid;
    private long bill_no;
    private float amount;
    private float Quantity;
    private Date date;
    private float discount_amount;
    private float discount_rate;
    private float net_amount;
    private String customer_fid;

    public int getBill_pid() {
        return bill_pid;
    }

    public void setBill_pid(int bill_pid) {
        this.bill_pid = bill_pid;
    }

    public long getBill_no() {
        return bill_no;
    }

    public void setBill_no(long bill_no) {
        this.bill_no = bill_no;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getQuantity() {
        return Quantity;
    }

    public void setQuantity(float quantity) {
        Quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(float discount_amount) {
        this.discount_amount = discount_amount;
    }

    public float getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(float discount_rate) {
        this.discount_rate = discount_rate;
    }

    public float getNet_amount() {
        return net_amount;
    }

    public void setNet_amount(float net_amount) {
        this.net_amount = net_amount;
    }

    public String getCustomer_fid() {
        return customer_fid;
    }

    public void setCustomer_fid(String customer_fid) {
        this.customer_fid = customer_fid;
    }

}
