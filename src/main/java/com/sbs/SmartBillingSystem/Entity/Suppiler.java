package com.sbs.SmartBillingSystem.Entity;

import jakarta.persistence.*;

@Entity
public class Suppiler {
	@Id
	private int supplier_pid;
	String code;
	private String name;
	private  String email;
	private String mobile_no;
	private String address;
	private String GST_no;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGST_no() {
		return GST_no;
	}

	public void setGST_no(String gST_no) {
		GST_no = gST_no;
	}

	public Suppiler(int supplier_pid, String code, String name, String mobile_no, String address, String gST_no) {
		super();
		this.supplier_pid = supplier_pid;
		this.code = code;
		this.name = name;
		this.mobile_no = mobile_no;
		this.address = address;
		GST_no = gST_no;
	}

	public int getSupplier_pid() {
		return supplier_pid;
	}

	public void setSupplier_pid(int supplier_pid) {
		this.supplier_pid = supplier_pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Suppiler(int supplier_pid, String name, String mobile_no, String address) {
		super();
		this.supplier_pid = supplier_pid;
		this.name = name;
		this.mobile_no = mobile_no;
		this.address = address;
	}

	public Suppiler() {
		super();
		// TODO Auto-generated constructor stub
	}

}
