package com.sbs.SmartBillingSystem.Entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Bill {
	@GeneratedValue
	@Id
	private int sale_pid;
	private long bill_no;
	private float amount;
	private float Quantity;
	private Date date;
	private float discount_amount;
	private float discount_rate;
	private float net_amount;
	@OneToOne
	private Customer customer_fid;
	@OneToOne
	private User user_fid;

	public Bill() {
		// TODO Auto-generated constructor stub
	}

}
