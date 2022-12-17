package com.guru.bot;

import com.guru.commands.CommandManager;
import com.guru.credentials.Credentials;
import com.guru.data.MemoryManagement;
import com.guru.data.StartupConfiguration;
import com.guru.userdata.UsersHandler;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class Guru {

	//always keep an instance of the main program, for convenience.
	private static final Guru instance = new Guru();
	
	
	//sub programs
	private DefaultShardManagerBuilder sharedManager;
	private MemoryManagement management;
	private CommandManager commandManager;
	private Credentials credentials;
	private UsersHandler usersHandler;
	
	private ShardManager JDA;
	
	private StartupConfiguration startupConfiguration;
	
	/**
	 * this initializes the program instance.
	 */
	public void launch() {

		this.management = new MemoryManagement();
		this.credentials = new Credentials();
		
		this.startupConfiguration = new StartupConfiguration();

		this.management.inject(credentials, startupConfiguration);

		this.sharedManager = DefaultShardManagerBuilder
							 		.createDefault(this.credentials.AUTH_KEY)
							 		.setStatus(this.startupConfiguration.getStatus())
							 		.enableIntents(GatewayIntent.MESSAGE_CONTENT);
		
		
		this.setUsersHandler(new UsersHandler(this.management));
		this.usersHandler.loadUserData();
		
		this.commandManager = new CommandManager();
		this.commandManager.loadCommands();
		
		this.sharedManager.addEventListeners(this.commandManager);
		
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


	public UsersHandler getUsersHandler() {
		return usersHandler;
	}


	public void setUsersHandler(UsersHandler usersHandler) {
		this.usersHandler = usersHandler;
	}
	
}
