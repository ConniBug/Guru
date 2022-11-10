package com.guru.data;

import com.guru.bot.Guru;

import net.dv8tion.jda.api.OnlineStatus;

@Configuration
public class StartupConfiguration {
	
	public StartupConfiguration() {
		Guru.getInstance().getManagement().getClazzScanner().includeClassInScanner(this);
	}

	@SerializableField(path = "profile.status")
	private OnlineStatus status;
	
	@SerializableField(path = "configuration.prefix")
	private String command_prefix;

	
	public OnlineStatus getStatus() {
		return status;
	}
	
	public String getCommandPrefix() {
		return this.command_prefix;
	}
}
