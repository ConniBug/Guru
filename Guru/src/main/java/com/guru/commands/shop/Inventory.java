package com.guru.commands.shop;

import java.awt.Color;

import com.guru.bot.Guru;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.commands.shop.items.ShopItem;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * help command class, this is responsible for showing information for all commands, akin to the linux command man
 * @author synte
 * @version 0.0.1 
 *
 */
@CommandMeta(name = {"inv", "inventory"}, description = "displays your inventory", category = Category.SHOP, usage = {"inventory"})
public class Inventory extends Command{

	public Inventory() {
		this.setAvailable(true);
	}
	
	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {

		EmbedBuilder embedBuilder = new EmbedBuilder();
		
		embedBuilder.setTitle("Codewars profile");
		embedBuilder.setColor(Color.orange);
		
		if(this.getUserModel(event).getInventory().getItems().isEmpty()) {
			embedBuilder.setDescription("You have nothing in your inventory.");
		}
		
		this.getUserModel(event).getInventory().getItems().forEach(o -> {
			ShopItem item = Guru.getInstance().getItemHandler().getItemById(o.getId()).get();
			embedBuilder.addField("[" + item.getMeta().emoji() + "] - " + item.getMeta().name()[0], "`Amount owned " + o.getAmount() + "`", true);
		});
		
		event.getMessage().replyEmbeds(embedBuilder.build()).queue();
		
	}




}
