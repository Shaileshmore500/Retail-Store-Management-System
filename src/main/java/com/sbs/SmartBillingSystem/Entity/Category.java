package com.sbs.SmartBillingSystem.Entity;

import jakarta.persistence.*;

@Entity
public class Category {
	@Id
	@GeneratedValue
	private int category_pid;
	private String name;
	@Column(unique = true)
	private String code;

	public int getCategory_pid() {
		return category_pid;
	}

	public void setCategory_pid(int category_pid) {
		this.category_pid = category_pid;
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

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(int category_pid, String name, String code) {
		super();
		this.category_pid = category_pid;
		this.name = name;
		this.code = code;
	}

}
