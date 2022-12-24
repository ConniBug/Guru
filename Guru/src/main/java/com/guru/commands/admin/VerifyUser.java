package com.guru.commands.admin;

import java.awt.Color;
import java.util.List;

import com.guru.bot.Guru;
import com.guru.codewars.modals.CodewarsMeta;
import com.guru.codewars.users.katas.Datum;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.userdata.CodewarsProfile;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(category = Category.ADMIN, description = "shows the current user to be verified", name = {"verify"}, usage = {"verify <@user>"}, permission = {"ADMINISTRATOR"})
public class VerifyUser extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		if(args.length != 1) {
			List<User> a = event.getMessage().getMentions().getUsers();
			if(a.size() > 0) {
				
				UserModel data = Guru.getInstance().getUsersHandler().getUserData(a.get(0));
				
				System.out.println(data);

				String name = data.getLink().split("users/")[1];
				CodewarsMeta meta = CodewarsMeta.fromName(name);
				List<Datum> katas = Datum.fromApi("https://www.codewars.com/api/v1/users/" + name);
				
				CodewarsProfile profile = new CodewarsProfile(data.getLink(), meta, katas);
				data.setCodewars(profile);
				data.setLink("");
				
				data.save();
				
				EmbedBuilder entries = new EmbedBuilder();
				entries.setTitle("Entries");
				entries.setDescription(a.get(0).getName() + " has been verified.");
				entries.setColor(Color.green);
				
				entries.addField("Codewars profile", "```css\n" + data.getCodewars().getMeta() + "```", false);
			
				
				event.getMessage().replyEmbeds(entries.build()).queue();
				
			}
		}else {
			throw new Exception("usage: " + this.getMeta().usage()[0]);
		}
		
	}
	
	
}
