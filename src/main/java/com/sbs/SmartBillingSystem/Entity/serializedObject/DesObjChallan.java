package com.sbs.SmartBillingSystem.Entity.serializedObject;

public class DesObjChallan {

    private int partychallan_pid;
    private String amount;
    private float quantity;
    private String challan_date;
    private String supplier_fid;
    private String branch_fid;
    private String purchase_date;
    private String challan_no;
    public void setPartychallan_pid(int partychallan_pid) {
        this.partychallan_pid = partychallan_pid;
    }

    public int getPartychallan_pid() {
        return partychallan_pid;
    }

    

    public String getChallan_no() {
        return challan_no;
    }

    public void setChallan_no(String challan_no) {
        this.challan_no = challan_no;
    }


    public DesObjChallan() {
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPartyChallan_pid(int partyChallan_pid) {
        this.partychallan_pid = partyChallan_pid;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setChallan_date(String challan_date) {
        this.challan_date = challan_date;
    }

    public void setSupplier_fid(String supplier_fid) {
        this.supplier_fid = supplier_fid;
    }

    public void setBranch_fid(String branch_fid) {
        this.branch_fid = branch_fid;
    }

    public int getPartyChallan_pid() {
        return partychallan_pid;
    }

    public String getAmount() {
        return amount;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getChallan_date() {
        return challan_date;
    }

    public String getSupplier_fid() {
        return supplier_fid;
    }

    public String getBranch_fid() {
        return branch_fid;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

}
