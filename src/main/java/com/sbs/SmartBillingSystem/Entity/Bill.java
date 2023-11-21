package com.sbs.SmartBillingSystem.Entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Bill {
	@GeneratedValue
	@Id
	private int bill_pid;
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

	public long getBill_no() {
		return bill_no;
	}

	public float getAmount() {
		return amount;
	}

	public float getQuantity() {
		return Quantity;
	}

	public Date getDate() {
		return date;
	}

	public float getDiscount_amount() {
		return discount_amount;
	}

	public float getDiscount_rate() {
		return discount_rate;
	}

	public float getNet_amount() {
		return net_amount;
	}

	public Customer getCustomer_fid() {
		return customer_fid;
	}

	public User getUser_fid() {
		return user_fid;
	}

	public void setBill_no(long bill_no) {
		this.bill_no = bill_no;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public void setQuantity(float quantity) {
		Quantity = quantity;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDiscount_amount(float discount_amount) {
		this.discount_amount = discount_amount;
	}

	public void setDiscount_rate(float discount_rate) {
		this.discount_rate = discount_rate;
	}

	public void setNet_amount(float net_amount) {
		this.net_amount = net_amount;
	}

	public void setCustomer_fid(Customer customer_fid) {
		this.customer_fid = customer_fid;
	}

	public void setUser_fid(User user_fid) {
		this.user_fid = user_fid;
	}

}
