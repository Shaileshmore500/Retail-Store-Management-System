package com.sbs.SmartBillingSystem.Entity.serializedObject;

public class DesObjChallan {

    private int PartyChallan_pid;
    private float amount;
    private float quantity;
    private String challan_date;
    private String supplier_fid;
    private String branch_fid;
    private String purchase_date;
    private String challan_no;

    public String getChallan_no() {
        return challan_no;
    }

    public void setChallan_no(String challan_no) {
        this.challan_no = challan_no;
    }

    @Override
    public String toString() {
        return "DesObjChallan [PartyChallan_pid=" + PartyChallan_pid + ", amount=" + amount + ", quantity=" + quantity
                + ", challan_date=" + challan_date + ", supplier_fid=" + supplier_fid + ", branch_fid=" + branch_fid
                + ", purchase_date=" + purchase_date + ", challan_no=" + challan_no + "]";
    }

    public DesObjChallan() {
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPartyChallan_pid(int partyChallan_pid) {
        PartyChallan_pid = partyChallan_pid;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setQuantity(float quantity) {
        quantity = quantity;
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
        return PartyChallan_pid;
    }

    public float getAmount() {
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
