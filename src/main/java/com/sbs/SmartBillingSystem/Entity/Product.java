package com.sbs.SmartBillingSystem.Entity;

import jakarta.persistence.*;

@Entity
public class Product {
	@Id
	@GeneratedValue
	private int product_pid;
	private String code;
	private String name;
	private String style;
	private float purchase_rate;
	private float mrp;
	private float quantity;
	private float total_amount;
	@OneToOne(cascade = CascadeType.PERSIST)
	private Brand brand_fid;
	@OneToOne(cascade = CascadeType.PERSIST)
	private Category category_fid;
	@OneToOne
	private Suppiler supplier_fid;
	private String barcode;
	@OneToOne
	private Challan challan_fid;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(int product_pid, String code, String name, String style, float purchase_rate, float mRP,
			float quantity, float total_amount, Brand brand_fid, Category category_fid, Suppiler supplier_fid,
			String barcode, Challan partyChallan_fid) {
		super();
		this.product_pid = product_pid;
		this.code = code;
		this.name = name;
		this.style = style;
		this.purchase_rate = purchase_rate;
		this.mrp = mRP;
		this.quantity = quantity;
		this.total_amount = total_amount;
		this.brand_fid = brand_fid;
		this.category_fid = category_fid;
		this.supplier_fid = supplier_fid;
		this.barcode = barcode;
		challan_fid = partyChallan_fid;
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

	public float getMRP() {
		return mrp;
	}

	public void setMRP(float mRP) {
		mrp = mRP;
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

	public Brand getBrand_fid() {
		return brand_fid;
	}

	public void setBrand_fid(Brand brand_fid) {
		this.brand_fid = brand_fid;
	}

	public Category getCategory_fid() {
		return category_fid;
	}

	public void setCategory_fid(Category category_fid) {
		this.category_fid = category_fid;
	}

	public Suppiler getSupplier_fid() {
		return supplier_fid;
	}

	public void setSupplier_fid(Suppiler supplier_fid) {
		this.supplier_fid = supplier_fid;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Challan getPartyChallan_fid() {
		return challan_fid;
	}

	public void setPartyChallan_fid(Challan partyChallan_fid) {
		challan_fid = partyChallan_fid;
	}

	@Override
	public String toString() {
		return "Product [product_pid=" + product_pid + ", code=" + code + ", name=" + name + ", style=" + style
				+ ", purchase_rate=" + purchase_rate + ", mrp=" + mrp + ", quantity=" + quantity + ", total_amount="
				+ total_amount + ", brand_fid=" + brand_fid + ", category_fid=" + category_fid + ", supplier_fid="
				+ supplier_fid + ", barcode=" + barcode + ", challan_fid=" + challan_fid + "]";
	}

}
