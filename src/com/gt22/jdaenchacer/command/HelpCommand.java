package com.gt22.jdaenchacer.command;

import java.util.List;
import java.util.function.Function;
import com.gt22.jdaenchacer.MessageHelper;;
public class HelpCommand extends Command
{
	public HelpCommand(String name, String shortname, String desc, String syntax, CommandAction nonadminaction, int level, CommandManager manager, Function<List<Command>, String> listFormater, Function<Command, String> commandFormater, String commandNotFoundReply)
	{
		super(name, shortname, desc, syntax, (msg, args, guild) -> {
			System.out.println(args.length);
			if(args.length == 0)
			{
				msg.getChannel().sendMessage(listFormater.apply(manager.getCommands()));
			}
			else 
			{
				Command cm = manager.getCommandByName(args[1]);
				if(cm == null)
				{
					MessageHelper.reply(msg, commandNotFoundReply);
				}
				else
				{
					msg.getChannel().sendMessage(commandFormater.apply(cm));
				}
			}
		}, nonadminaction, level);
	}
	
	public HelpCommand(String name, String shortname, String desc, String syntax, CommandManager manager, Function<List<Command>, String> listFormater, Function<Command, String> commandFormater, String commandNotFoundReply)
	{
		this(name, shortname, desc, syntax, null, 0, manager, listFormater, commandFormater, commandNotFoundReply);
	}
	
	public static HelpCommand getDefaultImpl(CommandManager manager)
	{
		return new HelpCommand("commands", "help", "Displays command list, if command provided as argument displays information about it", "[command]", manager, (commands) -> {
			String ret = "";
			for (Command cm : commands)
			{
				ret += String.format("• %s,\n", cm.name);
			}
			return String.format("```Commands:\n%sUse !commands <command> to get additional info```", ret);
		}, (cm) -> {
			return String.format("```"
					+ "Command %s:\n"
					+ "Required permission level: %s\n"
					+ "Short name: %s\n"
					+ "Syntax: %s\n"
					+ "%s"
					+ "```", cm.name, cm.level, cm.shortname, cm.syntax, cm.desc);
		}, "Sorry, cannot found that command");
	}
}
