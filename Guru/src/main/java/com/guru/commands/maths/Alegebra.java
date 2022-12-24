package com.guru.commands.maths;

import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.syngen.engine.SimplifyExpression;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(category = Category.MATHS, description = "evaluate a given mathmatical expression", name = {"eval", "calc", "math", "calculate", "evaluate"}, usage = {"eval <expression>"})
public class Alegebra extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		if(args.length == 0) {
			throw new Exception("Usage eval {expression}");
		}
		
		String expression = args[1];
		
		SimplifyExpression eval = SimplifyExpression.instance(expression);
		
		eval.simplify();
		
		StringBuilder ans = new StringBuilder();
		
		eval.workingOut.stream().distinct().forEach(i -> {
			ans.append(i + System.lineSeparator());
		});
		
		
		
		event.getMessage().reply("```" + ans.toString() + "```").queue();
		
		
	}
	
	/*
	@Override
	public List<Argument> options() {
		return Argument.builder(new Argument(OptionType.STRING, "expression", "the expression to be parsed", true, false));
	}
	 */
}
