package com.guru.commands;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.guru.reflection.CommandScanner;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * This class handles the initialisation of commands, as well as the storing of all commands
 * @author synte
 * @version 0.0.1
 * @see CommandManager#getCommands
 *
 */
public final class CommandManager extends ListenerAdapter{
	
	private List<Command> commands;
	
	/**
	 * load all the commands for this command manager instance
	 */
	public void loadCommands() {
		CommandScanner scanner = new CommandScanner();
		
		//initialise and inject dependency values to all commands
		this.commands = scanner.retrieveCommandsFromPackage("com.guru.commands");
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
	public Optional<Command> getCommandByClass(Class<Command> clazz) {
		return this.commands.stream().filter(i -> i.getClass() == clazz).findFirst();
	}
	

	public Optional<Command> getCommandByName(String alt) {
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
	public List<Command> getCatergoryCommands(Category category) {
		//create a command stream
		//filter all commands whos category is equal to the parameter category
		//collect all values in the pipeline to an arraylist
		//return the arraylist
		return commands.stream().filter(o -> o.getMeta().category() == category).collect(Collectors.toList());
	}
	
}

