package com.guru.commands;

public enum Category {

	HELP(":book:");
	
	private final String emoji;
	
	private Category(String emoji) {
		this.emoji = emoji;
	}
	
	public String getEmoji() {
		return this.emoji;
	}
	
}
