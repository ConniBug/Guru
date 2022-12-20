package com.guru.bot;

/**
 * This is the main class that gets the bot up and running.
 * @author syntex
 * @version 1.0.0
 */
public class Launcher {
	public static void main(String[] args) {
		//start the bot.
		Guru instance = new Guru();
		instance.start();
	}
}
