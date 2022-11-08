package com.guru.bot;

import com.guru.data.MemoryManagement;
import com.guru.data.StartupConfiguration;

import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

public class Guru {

	private static final Guru instance = new Guru();
	
	private DefaultShardManagerBuilder sharedManager;
	private MemoryManagement management;
	
	private StartupConfiguration startupConfiguration;
	
	public void launch() {

		this.management = new MemoryManagement();
		
		
		this.startupConfiguration = new StartupConfiguration();
		this.management.inject();

		this.sharedManager = DefaultShardManagerBuilder.createDefault(this.management.getCredentials().AUTH_KEY);
		this.sharedManager.setStatus(this.startupConfiguration.getStatus());
		
		
		this.sharedManager.addEventListeners(new TestEvent());
		this.sharedManager.build();
		
	
	}
	
	
	public static Guru getInstance() {
		return instance;
	}

	public MemoryManagement getManagement() {
		return management;
	}

	public StartupConfiguration getStartupConfiguration() {
		return startupConfiguration;
	}

	public DefaultShardManagerBuilder getSharedManager() {
		return sharedManager;
	}
	
}
