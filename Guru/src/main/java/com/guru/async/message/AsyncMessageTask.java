package com.guru.async.message;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

@FunctionalInterface
public interface AsyncMessageTask {
	public abstract EmbedBuilder submit(Message waitingMessage);
}
