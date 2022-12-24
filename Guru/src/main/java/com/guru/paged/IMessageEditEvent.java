package com.guru.paged;

import net.dv8tion.jda.api.utils.messages.MessageEditData;

@FunctionalInterface
public interface IMessageEditEvent {
	public abstract void onMessageEdit(MessageEditData e);
}
