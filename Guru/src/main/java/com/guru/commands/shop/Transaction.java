package com.guru.commands.shop;

import java.util.Date;

public class Transaction {
	
	private int amount;
	private String sender;
	private String reciever;
	private TransactionType type;
	
	private Date time;

	public Transaction(int amount, String sender, String reciever, Date time, TransactionType type) {
		this.amount = amount;
		this.sender = sender;
		this.reciever = reciever;
		this.time = time;
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	

}
