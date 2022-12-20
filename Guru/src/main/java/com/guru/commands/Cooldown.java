package com.guru.commands;

import java.util.Date;

import javax.annotation.Nonnull;

/**
 * Immutable class which represents a cooldown.
 * @author synte
 */
public final class Cooldown {

	private final String userID;
	private final Date startTime;
	private final Command command;
	
	public Cooldown(@Nonnull String user, @Nonnull Command command, @Nonnull Date startTime) {
		this.userID = user;
		this.startTime = startTime;
		this.command = command;
	}
	
	/**
	 * retrieve the user who is attached to this cooldown
	 * @return the user who this cooldown applies to
	 */
	public String getUserID() {
		return userID;
	}
	
	/**
	 * the Date when this cooldown was created
	 * @return the creation time for this cooldown
	 */
	public Date getStartTime() {
		return startTime;
	}
	
	/**
	 * return the command which is this cooldown applies to
	 * @return the command
	 */
	public Command getCommand() {
		return this.command;
	}
	
	/**
	 * finds the amount of time that this cooldown has remaining till the cooldown expires
	 * can be negative which represents duration over the cooldown
	 * @return the amount of time remaining for this cooldown in milliseconds
	 */
	public long timeRemaining() {
		
		long cooldownDuration = this.command.getMeta().cooldown();
		
		Date currentTime = new Date();
		
		long endTime = startTime.getTime() + (cooldownDuration*1000);
		long milli = endTime - currentTime.getTime();
		
		return milli;
	}
	
}
