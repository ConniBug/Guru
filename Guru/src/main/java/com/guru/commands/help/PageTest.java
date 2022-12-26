package com.guru.commands.help;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.paged.PagedEmbed;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * help command class, this is responsible for showing information for all commands, akin to the linux command man
 * @author synte
 * @version 0.0.1 
 *
 */
@CommandMeta(name = {"pagetest"}, description = "Displays the help command", category = Category.HELP, usage = {"pagetest"})
public class PageTest extends Command{

	
	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
	
		
		//event.getMessage().replyEmbeds(builder.build()).queue();
		
		List<String> list = IntStream.range(1, 26).mapToObj(String::valueOf).collect(Collectors.toList());
		
		PagedEmbed<String> embedBuilder = PagedEmbed.create(event, list, 12, true, o -> {
			EmbedBuilder builder = new EmbedBuilder();
			
			builder.setTitle("Page Test");
			builder.setColor(Color.cyan);
			builder.setDescription("You are currently viewing page " + o.getPage());
			
			o.forEach(a -> {
				builder.appendDescription(System.lineSeparator());
				builder.appendDescription("Element " + a);
			});
			
			return builder;	
		});
		
		embedBuilder.sendAsReply();
		
	}
	


}
