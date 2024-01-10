package com.sbs.SmartBillingSystem.Entity;

import jakarta.persistence.*;

@Entity
public class Customer {
	@Id
	private int customer_pid;
	private String name ;

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
