package com.guru.commands.codewars;

import java.awt.Color;

import org.json.JSONObject;

import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.userdata.UserModel;
import com.guru.utils.Network;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(name = {"cstats", "cprofile", "codewarsstats"}, description = "Displays a users codewars stats", category = Category.CODEWARS, usage = {"cstats", "cstats <user>"})
public class Stats extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args, UserModel model) throws Exception {
		
		System.out.println(args.length);
		
		StringBuilder name = new StringBuilder();
		
		String json = "";
		
		if(args.length == 1) {
			if(model.getCodewars().isEmpty()) {
				this.logError(event, "Sorry, you don't have a codewars profile linked please do ;link {profile} if you've already linked your account, please wait for a developer to verify your status.");
				return;
			}
			//System.out.println("https://www.codewars.com/api/v1/users/" + model.getCodewars().split("users/")[1]);
			json = Network.readURL("https://www.codewars.com/api/v1/users/" + model.getCodewars().split("users/")[1]);
		}else {
			for(int i = 1; i < args.length; i++) {
				name.append(args[i] + " ");
			}
			json = Network.readURL("https://www.codewars.com/api/v1/users/" + name.toString().trim().replace(" ", "%20").trim());
		}
		
		System.out.println(json);

		JSONObject obj = new JSONObject(json);

		EmbedBuilder embedBuilder = new EmbedBuilder();
		
		embedBuilder.setTitle("Codewars profile");
		embedBuilder.setColor(Color.cyan);
		embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
		embedBuilder.setDescription("here is " + obj.get("username") + " profile, this user is ranked #" + obj.get("leaderboardPosition") + " globaly.");
		
		embedBuilder.addField("Id" , "```" + obj.get("id") + "```", false);
		embedBuilder.addField("Global Rank" , "```" + obj.get("leaderboardPosition") + "```", false);
		embedBuilder.addField("Honor" , "```" + obj.get("honor") + "```", true);
		embedBuilder.addField("Clan" , "```" + obj.get("clan") + "```", true);
		embedBuilder.addField("Completed Kata" , "```" + obj.getJSONObject("codeChallenges").get("totalCompleted") + "```", true);
		
		embedBuilder.setFooter("if this seems unexpected, please contact @syntex#1389");
		
		event.getMessage().replyEmbeds(embedBuilder.build()).queue();
		
	}

}
