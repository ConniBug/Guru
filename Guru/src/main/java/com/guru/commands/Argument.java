package com.guru.commands;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public class Argument {
	
	private OptionType type;
	private String name;
	private String description;
	private boolean required;
	private boolean autoComplete;
	
	public Argument(@Nonnull OptionType type, String name, String description, boolean required, boolean autoComplete) {
		this.type = type;
		this.name = name;
		this.description = description;
		this.required = required;
		this.autoComplete = autoComplete;
	}

	public OptionType getType() {
		return type;
	}

	public void setType(OptionType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isAutoComplete() {
		return autoComplete;
	}

	public void setAutoComplete(boolean autoComplete) {
		this.autoComplete = autoComplete;
	}
	
	public static List<Argument> builder(Argument... args){
		List<Argument> data = new ArrayList<>();
		
		for(Argument o : args.clone()) {
			data.add(o);
		}
		
		return data;
	}
	
	public static List<Argument> EMPTY(){
		List<Argument> data = new ArrayList<>();
		return data;
	}	

}
