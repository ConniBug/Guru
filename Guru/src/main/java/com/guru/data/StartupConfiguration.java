package com.guru.data;

import net.dv8tion.jda.api.OnlineStatus;

@Configuration
public class StartupConfiguration {
	
	@SerializableField(path = "profile.status")
	private final OnlineStatus status = OnlineStatus.DO_NOT_DISTURB;
	
	@SerializableField(path = "configuration.prefix")
	private final String command_prefix = ";";

	
	public OnlineStatus getStatus() {
		return status;
	}
	
	public String getCommandPrefix() {
		return this.command_prefix;
	}
}
