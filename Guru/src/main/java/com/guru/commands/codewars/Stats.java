package com.guru.commands.codewars;

import java.awt.Color;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guru.bot.Guru;
import com.guru.codewars.modals.CodewarsMeta;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(name = {"cstats", "cprofile", "codewarsstats"}, description = "Displays a users codewars stats", category = Category.CODEWARS, usage = {"cstats", "cstats <@user>"})
public class Stats extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args, UserModel model) throws Exception {
		
		System.out.println(args.length);
		
		//StringBuilder name = new StringBuilder();
		
		CodewarsMeta meta = null;
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		if(args.length == 1) {
			if(!model.getCodewars().isRegistered()) {
				this.logError(event, "Sorry, you don't have a codewars profile linked please do ;link {profile} if you've already linked your account, please wait for a developer to verify your status.");
				return;
			}
			meta = model.getCodewars().getMeta();
		}else {
			//for(int i = 1; i < args.length; i++) {
			//	name.append(args[i] + " ");
			//}
			//String json = Network.readURL("https://www.codewars.com/api/v1/users/" + name.toString().trim().replace(" ", "%20").trim());
			
			//meta = gson.fromJson(json, CodewarsMeta.class);	
			
				List<User> a = event.getMessage().getMentions().getUsers();
				if(a.size() > 0) {
					model = Guru.getInstance().getUsersHandler().getUserData(a.get(0));
					if(!model.getCodewars().isRegistered()) {
						this.logError(event, "Sorry, " + a.get(0).getName() + " does not have a codewars profile linked please do ;link {profile} if you've already linked your account, please wait for a developer to verify your status.");
						return;
					}
					meta = model.getCodewars().getMeta();
				}
	
			
			
		}

		EmbedBuilder embedBuilder = new EmbedBuilder();
		
		embedBuilder.setTitle("Codewars profile");
		embedBuilder.setColor(Color.orange);
		//embedBuilder.setDescription("here is " + meta.getName() + " profile, this user is ranked #" + meta.getRanks() + " globaly.");
		
		//embedBuilder.addField("Id" , "```" + obj.get("id") + "```", false);
		//embedBuilder.addField("Global Rank" , "```" + obj.get("leaderboardPosition") + "```", false);
		//embedBuilder.addField("Honor" , "```" + obj.get("honor") + "```", true);
		//embedBuilder.addField("Clan" , "```" + obj.get("clan") + "```", true);
		//embedBuilder.addField("Completed Kata" , "```" + obj.getJSONObject("codeChallenges").get("totalCompleted") + "```", true);
		
		embedBuilder.setDescription("```json" + System.lineSeparator() + gson.toJson(meta) + "```");
		
		embedBuilder.setFooter("if this seems unexpected, please contact @syntex#1389");
		
		event.getMessage().replyEmbeds(embedBuilder.build()).queue();
		
	}

}
