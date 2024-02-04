package com.sbs.SmartBillingSystem.Entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class BillDetails {
    @GeneratedValue
    @Id
    private int billDetails_pid;
    private float Quantity;
    private float discount_amount;
    private float discount_rate;
    private float net_amount;
    @OneToOne
    private Product product_fid;
    @OneToOne
    private Bill bill_fid;

    public BillDetails() 
    {
    }

    public void setBillDetails_pid(int billDetails_pid) {
        this.billDetails_pid = billDetails_pid;
    }

    public void setQuantity(float quantity) {
        Quantity = quantity;
    }

    public void setDiscount_amount(float discount_amount) {
        this.discount_amount = discount_amount;
    }

    public void setDiscount_rate(float discount_rate) {
        this.discount_rate = discount_rate;
    }

    public void setNet_amount(float net_amount) {
        this.net_amount = net_amount;
    }

    public void setProduct_fid(Product product_fid) {
        this.product_fid = product_fid;
    }

    public void setBill_fid(Bill bill_fid) {
        this.bill_fid = bill_fid;
    }

    public int getBillDetails_pid() {
        return billDetails_pid;
    }

    public float getQuantity() {
        return Quantity;
    }

    public float getDiscount_amount() {
        return discount_amount;
    }

    public float getDiscount_rate() {
        return discount_rate;
    }

    public float getNet_amount() {
        return net_amount;
    }

    public Product getProduct_fid() {
        return product_fid;
    }

    public Bill getBill_fid() {
        return bill_fid;
    }

}
