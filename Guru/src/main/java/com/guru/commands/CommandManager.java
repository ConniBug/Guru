package com.guru.commands;

import java.util.List;
import java.util.stream.Collectors;

import com.guru.reflection.CommandScanner;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter{
	
	private List<Command> commands;
	
	public void loadCommands() {
		CommandScanner scanner = new CommandScanner();
		this.commands = scanner.retrieveCommandsFromPackage("com.guru.commands");
	}

	public List<Command> getCommands() {
		return commands;
	}
	
	public List<Command> getCatergoryCommands(Category category) {
		return commands.stream().filter(o -> o.getMeta().category() == category).collect(Collectors.toList());
	}
	
}
