package com.sbs.SmartBillingSystem.Entity.serializedObject;

public class DesObjProduct {

    private int product_pid;
    private String code;
    private String name;
    private String style;
    private float purchase_rate;
    private float mrp;
    private float quantity;
    private float total_amount;

    private int brand_fid;

    private int category_fid;

    private int supplier_fid;
    private String barcode;

    private int challan_fid;

    public DesObjProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int getProduct_pid() {
        return product_pid;
    }

    public void setProduct_pid(int product_pid) {
        this.product_pid = product_pid;
    }

    public String getCode() {
        return code;
    }

    public DesObjProduct(int product_pid, String code, String name, String style, float purchase_rate, float mrp,
            float quantity, float total_amount, int brand_fid, int category_fid, int supplier_fid, String barcode,
            int challan_fid) {
        this.product_pid = product_pid;
        this.code = code;
        this.name = name;
        this.style = style;
        this.purchase_rate = purchase_rate;
        this.mrp = mrp;
        this.quantity = quantity;
        this.total_amount = total_amount;
        this.brand_fid = brand_fid;
        this.category_fid = category_fid;
        this.supplier_fid = supplier_fid;
        this.barcode = barcode;
        this.challan_fid = challan_fid;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public float getPurchase_rate() {
        return purchase_rate;
    }

    public void setPurchase_rate(float purchase_rate) {
        this.purchase_rate = purchase_rate;
    }

    public float getMrp() {
        return mrp;
    }

    public void setMrp(float mrp) {
        this.mrp = mrp;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public int getBrand_fid() {
        return brand_fid;
    }

    public void setBrand_fid(int brand_fid) {
        this.brand_fid = brand_fid;
    }

    public int getCategory_fid() {
        return category_fid;
    }

    public void setCategory_fid(int category_fid) {
        this.category_fid = category_fid;
    }

    public int getSupplier_fid() {
        return supplier_fid;
    }

    public void setSupplier_fid(int supplier_fid) {
        this.supplier_fid = supplier_fid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getChallan_fid() {
        return challan_fid;
    }

    public void setChallan_fid(int challan_fid) {
        this.challan_fid = challan_fid;
    }

}
