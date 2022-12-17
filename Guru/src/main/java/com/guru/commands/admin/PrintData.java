package com.guru.commands.admin;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guru.bot.Guru;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(category = Category.ADMIN, description = "print out a users data", name = {"json"}, usage = {"json <@user>"}, permission = {"ADMINISTRATOR"})
public class PrintData extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args, UserModel model) throws Exception {
		

		if(args.length != 1) {
			List<User> a = event.getMessage().getMentions().getUsers();
			if(a.size() > 0) {
				
				UserModel data = Guru.getInstance().getUsersHandler().getUserData(a.get(0).getId());
				
				Gson gson = new GsonBuilder()
				        		.setPrettyPrinting()
				        		.create();
				
				String json = gson.toJson(data);
				
				event.getMessage().reply("```json" + System.lineSeparator()  + json +  System.lineSeparator() + "```").queue();
				
			}
		}else {
			throw new Exception("usage: " + this.getMeta().usage()[0]);
		}
		
	}

	
}
