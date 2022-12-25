package com.guru.bot;

import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.guru.codewars.kata.KataCasher;
import com.guru.commands.CommandManager;
import com.guru.commands.shop.ItemHandler;
import com.guru.credentials.Credentials;
import com.guru.dailychallenges.DailyChallenges;
import com.guru.data.MemoryManagement;
import com.guru.data.StartupConfiguration;
import com.guru.logger.Logger;
import com.guru.userdata.UsersHandler;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

/**
 * core class, instantiates the jda instance, and loads everything up
 * @author synte
 *
 */
public class Guru extends ListenerAdapter{

	//always keep an instance of the main program, for convenience.
	private static Guru instance;
	
	
	//sub programs
	private final DefaultShardManagerBuilder sharedManager;
	private final MemoryManagement management;
	private final CommandManager commandManager;
	private final Credentials credentials;
	private final UsersHandler usersHandler;
	private final KataCasher kataCasher;
	private DailyChallenges dailyChallenges;
	private final ItemHandler itemHandler;
	
	private final ExecutorService service;
	private final int WORKER_THREADS = 3;

	
	private ShardManager JDA;
	
	private final StartupConfiguration startupConfiguration;
	
	public Credentials getCredentials() {
		return credentials;
	}

	public KataCasher getKataCasher() {
		return kataCasher;
	}

	public void setJDA(ShardManager jDA) {
		JDA = jDA;
	}

	/**
	 * this initialises the program instance.
	 */
	public Guru() {
		
		Guru.instance = this;
	
		this.service = Executors.newFixedThreadPool(this.WORKER_THREADS);

		this.dailyChallenges = new DailyChallenges();
		
		this.management = new MemoryManagement();
		this.credentials = new Credentials();
		
		this.startupConfiguration = new StartupConfiguration();

		this.kataCasher = new KataCasher();
		this.kataCasher.casheKatas();
		
		this.management.inject(credentials, startupConfiguration);
		
		this.itemHandler = new ItemHandler();
		
		//instantiate the jda instance, this is done to login to discord
		this.sharedManager = DefaultShardManagerBuilder
							 		.createDefault(this.credentials.AUTH_KEY)
							 		.setStatus(this.startupConfiguration.getStatus())
							 		.enableIntents(EnumSet.allOf(GatewayIntent.class));
		
		
		this.usersHandler = new UsersHandler(this.management);
		this.usersHandler.loadUserData();
		
		this.commandManager = new CommandManager();
		this.commandManager.loadCommands();
		
		this.sharedManager.addEventListeners(this, this.commandManager);
	
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
	
	
	@Override
	public void onGuildReady(GuildReadyEvent event) {
		super.onGuildReady(event);
		if(event.getGuild().getId().equals(DailyChallenges.GUILD_ID)) {
			Logger.INFO("Starting up daily challenges for " + event.getGuild().getName());
			this.dailyChallenges.start();
		}
	
	}

	public ExecutorService getExecutorService() {
		return service;
	}

	public ItemHandler getItemHandler() {
		return itemHandler;
	}

	
}
