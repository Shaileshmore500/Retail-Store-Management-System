package com.sbs.SmartBillingSystem.Entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Bill {
	@GeneratedValue
	@Id
	private int bill_pid;
	private float amount;
	private float Quantity;
	private Date date;
	private float discount_amount;
	private float discount_rate;
	private float net_amount;
	@OneToOne(cascade = CascadeType.ALL)
	private Customer customer_fid;
	@OneToOne(cascade = CascadeType.ALL)
	private User user_fid;
	private String payment_type;
	//	@Transient
//	private String _user_fid;
	@Transient
	private String _customer_fid;
	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}



	public Bill() {
		// TODO Auto-generated constructor stub
	}

	public String get_customer_fid() {
		return _customer_fid;
	}

	public void set_customer_fid(String _customer_fid) {
		this._customer_fid = _customer_fid;
	}


	public int getBill_pid() {
		return bill_pid;
	}

	public void setBill_pid(int bill_pid) {
		this.bill_pid = bill_pid;
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
	@PrePersist
    @PreUpdate
    public void prePersist() {
        this._customer_fid = (customer_fid != null) ? customer_fid.getCustomer_pid()+"" : null;
    }

}
