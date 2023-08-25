package com.sbs.SmartBillingSystem.Entity;

import jakarta.persistence.*;

@Entity
public class Designation {
	@Id
	private int designation_pid;
	private String name;
	private String code;

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

	public Designation(int designation_pid, String name, String code) {
		super();
		this.designation_pid = designation_pid;
		this.name = name;
		this.code = code;
	}

	public int getDesignation_pid() {
		return designation_pid;
	}

	public Designation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setDesignation_pid(int designation_pid) {
		this.designation_pid = designation_pid;
	}

}
