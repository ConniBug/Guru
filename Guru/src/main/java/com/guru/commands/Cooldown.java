package com.guru.commands;

import java.util.Date;

public class Cooldown {

	private String user;
	private Date startTime;
	private Command command;
	
	public Cooldown(String user, Command command, Date startTime) {
		this.user = user;
		this.startTime = startTime;
		this.command = command;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public long timeRemaining() {
		
		long time = this.command.getMeta().cooldown();
		
		Date current = new Date();
		
		long milli = (startTime.getTime() + (time*1000)) - current.getTime();
		
		System.out.println(milli);
		
		return milli;
	}
	
}
