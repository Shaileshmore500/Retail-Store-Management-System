package com.sbs.SmartBillingSystem.Entity;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int product_pid;
	private String code;
	private String name;
	private String style;
	private float purchase_rate;
	private float mrp;
	private float size;
	private float discountper;
	private float discountamt;
	private float quantity;
	private float netamount;
	private float total_amount;
	@OneToOne(cascade = CascadeType.PERSIST)
	private Brand brand_fid;
	@OneToOne(cascade = CascadeType.PERSIST)
	private Category category_fid;
	@OneToOne
	private Suppiler supplier_fid;
	private String barcode;
	@OneToOne
	// @OneToMany(fetch = FetchType.EAGER)
	private Challan challan_fid;

	private LocalDate createdDate;

	@PrePersist
	public void prePersist() {
		if (createdDate == null) {
			// Set the default value to today's date
			createdDate = LocalDate.now();
		}
	}

	public void setDiscountper(float discountper) {
		this.discountper = discountper;
	}

	public void setDiscountamt(float discountamt) {
		this.discountamt = discountamt;
	}

	public float getDiscountper() {
		return discountper;
	}

	public float getDiscountamt() {
		return discountamt;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public int getProduct_pid() {
		return product_pid;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getStyle() {
		return style;
	}

	public float getPurchase_rate() {
		return purchase_rate;
	}

	public float getMrp() {
		return mrp;
	}

	public float getSize() {
		return size;
	}

	public float getQuantity() {
		return quantity;
	}

	public float getTotal_amount() {
		return total_amount;
	}

	public Brand getBrand_fid() {
		return brand_fid;
	}

	public Category getCategory_fid() {
		return category_fid;
	}

	public Suppiler getSupplier_fid() {
		return supplier_fid;
	}

	public String getBarcode() {
		return barcode;
	}

	public Challan getChallan_fid() {
		return challan_fid;
	}

	public void setProduct_pid(int product_pid) {
		this.product_pid = product_pid;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setPurchase_rate(float purchase_rate) {
		this.purchase_rate = purchase_rate;
	}

	public void setMrp(float mrp) {
		this.mrp = mrp;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public void setTotal_amount(float total_amount) {
		this.total_amount = total_amount;
	}

	public void setBrand_fid(Brand brand_fid) {
		this.brand_fid = brand_fid;
	}

	public void setCategory_fid(Category category_fid) {
		this.category_fid = category_fid;
	}

	public void setSupplier_fid(Suppiler supplier_fid) {
		this.supplier_fid = supplier_fid;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setChallan_fid(Challan challan_fid) {
		this.challan_fid = challan_fid;
	}

	public float getNetamount() {
		return netamount;
	}

	public void setNetamount(float netamount) {
		this.netamount = netamount;
	}

}
