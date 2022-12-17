package com.guru.commands;

/*
 * This is used to categorize the types of commands, each category is associated with an emoji
 */
public enum Category {

	HELP(":book:"), HOLIDAYS(":ocean:"), MATHS(":pencil:"), CODEWARS(":computer:"), ADMIN(":tools:"), MONEY(":moneybag:");
		
	private final String emoji;
	
	private Category(String emoji) {
		this.emoji = emoji;
	}
	
	/**
	 * @return the emoji string
	 */
	public String getEmoji() {
		return this.emoji;
	}
	
}
