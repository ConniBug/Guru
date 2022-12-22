package com.guru.commands.codewars;

import java.awt.Color;

import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


/**
 * TODO: link command
 * @author synte
 *
 */
@CommandMeta(name = {"link"}, cooldown = 10L, description = "links your codewars account to discord, will require verification. please include something in your codewars profile to help us identify it's really you, such as leaving your discord name in the description or having the same profile, if it's not clear we may ask you some questions for verification.", category = Category.CODEWARS, usage = {"link <name>", "link <url>"})
public class Link extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args, UserModel model) throws Exception {

		if(args.length == 1 || !args[1].startsWith("https://www.codewars.com/users/")) {
			throw new Exception("please do ;link https://www.codewars.com/users/{YOURNAME}");
		}
		
		StringBuilder name = new StringBuilder();
		
		for(int i = 1; i < args.length; i++) {
			name.append(args[i] + " ");
		}
		
		EmbedBuilder embedBuilder = new EmbedBuilder();
		
		embedBuilder.setColor(Color.orange);
		embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
		
		embedBuilder.addField("Verification pending...", "We saved your codewars url, once approved your account will be linked to the specified codewars account.", false);

		embedBuilder.setFooter("for anyone rated kyu 4+ we may ask further questions to ensure it's really you.");
		
		event.getMessage().replyEmbeds(embedBuilder.build()).queue();
		
		model.createCodeWarsLink(name.toString().trim());
		model.save();
		
	}

}
