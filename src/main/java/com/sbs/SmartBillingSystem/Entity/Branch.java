package com.sbs.SmartBillingSystem.Entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Branch {
	@Id
	private int branch_pid;
	private String name;
	private String code;
	private String address;

	@OneToOne
	private Company ho;
	private Date creating_date;

	public Branch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getBranch_pid() {
		return branch_pid;
	}

	public void setBranch_pid(int branch_pid) {
		this.branch_pid = branch_pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Company getHo() {
		return ho;
	}

	public void setHo(Company ho) {
		this.ho = ho;
	}

	public Date getCreating_date() {
		return creating_date;
	}

	public void setCreating_date(Date creating_date) {
		this.creating_date = creating_date;
	}

	public Branch(int branch_pid, String name, String code, String address, Company ho, Date creating_date) {
		super();
		this.branch_pid = branch_pid;
		this.name = name;
		this.code = code;
		this.address = address;
		this.ho = ho;
		this.creating_date = creating_date;
	}

}
