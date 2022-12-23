package com.guru.bot;

import com.guru.logger.Logger;

/**
 * This is the main class that gets the bot up and running.
 * @author syntex
 * @version 1.0.0
 */
public class Launcher {
	
	private static final String version = "v0.0.5";
	
	public static void main(String[] args) {
		//start the bot.
		
		Logger.INFO("Starting up Guru " + version);
		System.exit(0);
		
		Guru instance = new Guru();
		instance.start();		
	}
}
