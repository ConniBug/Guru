package com.guru.data;

import com.guru.bot.Guru;

import net.dv8tion.jda.api.OnlineStatus;

public class StartupConfiguration {
	
	public StartupConfiguration() {
		Guru.getInstance().getManagement().getClazzScanner().includeClassInScanner(this);
	}

	@SerializableField(path = "profile.status")
	private OnlineStatus status;

	
	public OnlineStatus getStatus() {
		return status;
	}
}
