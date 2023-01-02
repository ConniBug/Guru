package com.guru.async.message;

import java.awt.Color;

import com.guru.bot.Guru;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AsyncMessageSender {
	
	private MessageReceivedEvent event;
	private EmbedBuilder waitingEmbed;
	private AsyncMessageTask asyncEvent;
	
	public AsyncMessageSender(MessageReceivedEvent event, EmbedBuilder waitingEmbed,
			AsyncMessageTask asyncEvent) {
		this.event = event;
		this.waitingEmbed = waitingEmbed;
		this.asyncEvent = asyncEvent;
	}
	
	public static AsyncMessageSender generateUserUpdate(MessageReceivedEvent event, AsyncMessageTask asyncEvent) throws Exception {
		
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setTitle("Update handler");
		embedBuilder.setColor(Color.cyan);
		embedBuilder.setThumbnail("https://flevix.com/wp-content/uploads/2020/01/Quarter-Circle-Loading.svg");
		embedBuilder.setDescription("We are currently in the process of updating this users data, please hold for a few seconds...");
		
		AsyncMessageSender sender = new AsyncMessageSender(event, embedBuilder, asyncEvent);
		
		return sender;
	}
	
	public static AsyncMessageSender generateMessageSender(MessageReceivedEvent event, EmbedBuilder builder, AsyncMessageTask asyncEvent) {
		
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setTitle("Update handler");
		embedBuilder.setColor(Color.cyan);
		embedBuilder.setThumbnail("https://flevix.com/wp-content/uploads/2020/01/Quarter-Circle-Loading.svg");
		embedBuilder.setDescription("We are currently in the process of updating this users data, please hold for a few seconds...");
		
		AsyncMessageSender sender = new AsyncMessageSender(event, embedBuilder, asyncEvent);
		
		return sender;
	}
	
	public static Message getUpdateMessage(MessageReceivedEvent event) {	

		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setTitle("Update handler");
		embedBuilder.setColor(Color.cyan);
		embedBuilder.setThumbnail("https://flevix.com/wp-content/uploads/2020/01/Quarter-Circle-Loading.svg");
		embedBuilder.setDescription("We are currently in the process of updating this users data, please hold for a few seconds...");
		
		return event.getMessage().replyEmbeds(embedBuilder.build()).complete();
		
	}
	
	public void queue() {	
		//pass to service executer to avoid the main thread from getting blocked.
		Guru.getInstance().getExecutorService().execute(() -> {
			
			//send the waiting message
			Message message = event.getMessage().replyEmbeds(this.waitingEmbed.build()).complete();
			
			//get the embed, which may take some time
			EmbedBuilder embed = asyncEvent.submit(message);
			
			//check if the message built successfully
			if(embed == null) {
				//delete the waiting message.
				message.delete().queue();
				return;
			}
			
			//send the message
			message.editMessageEmbeds(embed.build()).queue();
			
		});
	}
	
	public void sendWithoutEdit() {
		//pass to service executer to avoid the main thread from getting blocked.
		Guru.getInstance().getExecutorService().execute(() -> {
			//send the waiting message
			event.getMessage().replyEmbeds(this.waitingEmbed.build()).queue();
		});
	}
	
	public MessageReceivedEvent getEvent() {
		return event;
	}
	public EmbedBuilder getWaitingEmbed() {
		return waitingEmbed;
	}
	public AsyncMessageTask getAsyncEvent() {
		return asyncEvent;
	}
	public void setEvent(MessageReceivedEvent event) {
		this.event = event;
	}
	public void setWaitingEmbed(EmbedBuilder waitingEmbed) {
		this.waitingEmbed = waitingEmbed;
	}
	public void setAsyncEvent(AsyncMessageTask asyncEvent) {
		this.asyncEvent = asyncEvent;
	}


	

}
