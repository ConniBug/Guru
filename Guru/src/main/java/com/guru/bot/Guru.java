package com.guru.bot;

import com.guru.commands.CommandManager;
import com.guru.data.MemoryManagement;
import com.guru.data.StartupConfiguration;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class Guru {

	private static final Guru instance = new Guru();
	
	private DefaultShardManagerBuilder sharedManager;
	private MemoryManagement management;
	private CommandManager commandManager;
	
	private ShardManager JDA;
	
	private StartupConfiguration startupConfiguration;
	
	public void launch() {

		this.management = new MemoryManagement();
		
		
		this.startupConfiguration = new StartupConfiguration();
		this.management.inject();

		this.sharedManager = DefaultShardManagerBuilder
							 		.createDefault(this.management.getCredentials().AUTH_KEY)
							 		.setStatus(this.startupConfiguration.getStatus())
							 		.enableIntents(GatewayIntent.MESSAGE_CONTENT);
		
		
		this.commandManager = new CommandManager();
		this.commandManager.loadCommands();
		
		
		//System.exit(0);
		JDA = this.sharedManager.build();
		
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


	public CommandManager getCommandManager() {
		return commandManager;
	}


	public void setCommandManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}


	public void setSharedManager(DefaultShardManagerBuilder sharedManager) {
		this.sharedManager = sharedManager;
	}


	public void setManagement(MemoryManagement management) {
		this.management = management;
	}


	public void setStartupConfiguration(StartupConfiguration startupConfiguration) {
		this.startupConfiguration = startupConfiguration;
	}


	public ShardManager getJDA() {
		return JDA;
	}
	
}
