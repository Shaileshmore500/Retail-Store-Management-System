package com.sbs.SmartBillingSystem.Entity;

import jakarta.persistence.*;

@Entity
public class Customer {
	@Id
	private int customer_pid;
	private String name ;

	public Customer() {
	

		// TODO Auto-generated constructor stub
	}

}
