package com.guru.commands.shop;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.commands.shop.items.ShopItem;
import com.guru.reflection.ShopScanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * help command class, this is responsible for showing information for all commands, akin to the linux command man
 * @author synte
 * @version 0.0.1 
 *
 */
@CommandMeta(name = {"shop"}, description = "Displays the help command", category = Category.MONEY, usage = {"help", "help <command>"})
public class Shop extends Command{

	private List<ShopItem> items;
	
	public Shop() {
		this.setAvailable(false);
		
		ShopScanner scanner = new ShopScanner();
		
		this.setItems(scanner.retrieveCommandsFromPackage("com.guru.commands.shop.items"));
		
		//this horrible chunk of code was copied from the command handler class
		Set<ShopItem> duplicates = new HashSet<>();
		for(ShopItem command : this.items) {
			String[] commands = command.getMeta().name();
			for(ShopItem cmd2 : this.items) {
				if(command.equals(cmd2)) continue;
				String[] commands2 = cmd2.getMeta().name();
				parent: for(String o : commands) {
					for(String k : commands2) {
						if(k.equals(o)) {
							duplicates.add(command);
							break parent;
						}
					}
				}
			}
		}
		duplicates.forEach(o -> {
			System.err.println("duplicate items -> " + Arrays.toString(o.getMeta().name()) + " from " + o.getClass().getName());
		});
		if(duplicates.size() > 0) System.exit(0);
		
	}
	
	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
	
		EmbedBuilder shopEmbed = new EmbedBuilder();
		
		shopEmbed.setTitle("Shop");
		shopEmbed.setColor(Color.cyan);
		
		for(int i = 0; i < items.size(); i++) {
			ShopItem item = items.get(i);
			
			String n = (i < 10) ? "0"+(i+1)+"." : (i+1)+".";
			
			shopEmbed.addField("`" + n + "`\t " + item.getMeta().name()[0] + " - " + item.getMeta().cost() + " bablons", "```" + item.getMeta().description() + "```", false);
		}
		
		shopEmbed.setFooter("do ;buy {item number} {amount}(optional) to purchase something");
		
		event.getMessage().replyEmbeds(shopEmbed.build()).queue();
		
	}

	public List<ShopItem> getItems() {
		return items;
	}

	public void setItems(List<ShopItem> items) {
		this.items = items;
	}




}
