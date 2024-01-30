package com.sbs.SmartBillingSystem.Entity;



import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Customer {
	@Id
	@GeneratedValue
	private int customer_pid;
	private String name ;
	private String email ;
	private String mobile_no ;
	private Date dob ;
	private String address ;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(java.util.Date date) {
		this.dob = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCustomer_pid() {
		return customer_pid;
	}

	public void setCustomer_pid(int customer_pid) {
		this.customer_pid = customer_pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Customer() {
	

		// TODO Auto-generated constructor stub
	}

}
