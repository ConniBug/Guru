package com.guru.bot;

import com.guru.commands.CommandManager;
import com.guru.credentials.Credentials;
import com.guru.data.MemoryManagement;
import com.guru.data.StartupConfiguration;
import com.guru.userdata.UsersHandler;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

/**
 * core class, instantiates the jda instance, and loads everything up
 * @author synte
 *
 */
public class Guru {

	//always keep an instance of the main program, for convenience.
	private static Guru instance;
	
	
	//sub programs
	private final DefaultShardManagerBuilder sharedManager;
	private final MemoryManagement management;
	private final CommandManager commandManager;
	private final Credentials credentials;
	private final UsersHandler usersHandler;
	
	private ShardManager JDA;
	
	private final StartupConfiguration startupConfiguration;
	
	/**
	 * this initialises the program instance.
	 */
	public Guru() {
		
		Guru.instance = this;

		this.management = new MemoryManagement();
		this.credentials = new Credentials();
		
		this.startupConfiguration = new StartupConfiguration();

		this.management.inject(credentials, startupConfiguration);
		
		//instantiate the jda instance, this is done to login to discord
		this.sharedManager = DefaultShardManagerBuilder
							 		.createDefault(this.credentials.AUTH_KEY)
							 		.setStatus(this.startupConfiguration.getStatus())
							 		.enableIntents(GatewayIntent.MESSAGE_CONTENT);
		
		
		this.usersHandler = new UsersHandler(this.management);
		this.usersHandler.loadUserData();
		
		this.commandManager = new CommandManager();
		this.commandManager.loadCommands();
		
		this.sharedManager.addEventListeners(this.commandManager);
	}
	
	public void start() {
		this.JDA = this.sharedManager.build();
	}
	
	
	/**
	 * returns an instance of this class
	 * @return an instance of this class, as this is a singleton instantiating more than one class should not be needed
	 */
	public static Guru getInstance() {
		return Guru.instance;
	}
	
	public static void setGuru(Guru guru) {
		Guru.instance = guru;
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

	public ShardManager getJDA() {
		return JDA;
	}


	public UsersHandler getUsersHandler() {
		return usersHandler;
	}

	
}
