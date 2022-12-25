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

		ShopItem shopItem = Guru.getInstance().getItemHandler().getItemById(1).get();
		this.getUserModel(event).getInventory().addItem(shopItem);
		
		EmbedBuilder embedBuilder = new EmbedBuilder();
		
		embedBuilder.setTitle("Codewars profile");
		embedBuilder.setColor(Color.orange);
		
		this.getUserModel(event).getInventory().getItems().forEach(o -> {
			//embedBuilder.addField(o.getName(), o.getAmount()+"", true);
		});
		
		event.getMessage().replyEmbeds(embedBuilder.build()).queue();
		
	}




}
