package com.guru.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.guru.logger.Logger;
import com.guru.reflection.CommandScanner;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * This class handles the initialisation of commands, as well as the storing of all commands
 * @author synte
 * @version 0.0.1
 * @see CommandManager#getCommands
 *
 */
public final class CommandManager extends ListenerAdapter{
	
	public volatile boolean command_loaded = false;
	private List<Command> commands;
	
	/**
	 * load all the commands for this command manager instance
	 */
	public void loadCommands() {
		CommandScanner scanner = new CommandScanner();
		
		//initialise and inject dependency values to all commands
		this.commands = scanner.retrieveCommandsFromPackage("com.guru.commands");
		this.command_loaded = true;
		
		//check if multiple commands with the same name load
		//very bad code, i know...
		Set<Command> duplicates = new HashSet<>();
		for(Command command : this.commands) {
			String[] commands = command.getMeta().name();
			for(Command cmd2 : this.commands) {
				if(command.equals(cmd2)) continue;
				String[] commands2 = cmd2.getMeta().name();
				parent: for(String o : commands) {
					for(String k : commands2) {
						if(k.equals(o)) {
							duplicates.add(command);
							break parent;
						}
					}
				}
			}
		}
		
		if(duplicates.size() > 0) {
			duplicates.forEach(o -> {
				System.err.println("duplicate command -> " + Arrays.toString(o.getMeta().name()) + " from " + o.getClass().getName());
			});	
			System.exit(0);
		}
		
		
		
	}

	/**
	 * @return all the commands registered
	 */
	public List<Command> getCommands() {
		return commands;
	}
	
	/**
	 * retrieve the command who's class is the parameter
	 * @return the command if exists
	 * @param the class of the command
	 */
	public Optional<Command> getCommandByClass(@Nonnull Class<Command> clazz) {
		return this.commands.stream().filter(i -> i.getClass() == clazz).findFirst();
	}
	

	/**
	 * return the command if any by it's name
	 * @param alt, one of the names for this command
	 * @return
	 */
	public Optional<Command> getCommandByName(@Nonnull String alt) {
		for(Command i : this.commands) {
			for(String o : i.getMeta().name()) {
				if(o.equalsIgnoreCase(alt)) {
					return Optional.of(i);
				}
			}
		}
		return Optional.empty();
	}
	
	/**
	 * returns all commands under a specific category
	 * @param category of all the returned commands
	 * @return
	 */
	public List<Command> getCatergoryCommands(@Nonnull Category category) {
		return commands.stream().filter(o -> o.getMeta().category() == category).collect(Collectors.toList());
	}
	
	
	
	@Deprecated
	public void onGuildReady1(GuildReadyEvent event) {
		// TODO Auto-generated method stub
		super.onGuildReady(event);
		
		
		while(!this.command_loaded) {
			Logger.INFO("Waiting for commands to load, before adding guild commands.");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		Logger.INFO("Loading guild commands.");

		Guild guild = event.getGuild();
		
		List<SlashCommandData> cmds = new ArrayList<>();
		
		//cmds.add(Commands.slash("c", "a").addOption(OptionType.BOOLEAN, "a", "b", true, false).addOption(OptionType.CHANNEL, "c", "d", true, false));
		
		this.getCommands().forEach(i -> {
			
			SlashCommandData slashCommand = Commands.slash(i.getMeta().name()[0], i.getMeta().description());

		
			//for(Argument o : i.options()) {
			//	slashCommand.addOption(o.getType(), o.getName(), o.getDescription(), o.isRequired(), o.isAutoComplete());
			//}
			
			
			cmds.add(slashCommand);
			
		});

		guild.updateCommands().addCommands(cmds).queue();
		
	}
	 
	
}

