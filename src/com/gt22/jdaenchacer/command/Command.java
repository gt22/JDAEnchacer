package com.gt22.jdaenchacer.command;

import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;

public class Command {
	
	public static interface CommandAction
	{
		public void performCommand(Message msg, String[] args, Guild giuld);
	}
	
	public String name, shortname, desc, syntax;
	public CommandAction action, nonadminaction;
	public int level;

	public Command(String name, String shortname, String desc, String syntax, CommandAction action, CommandAction nonadminaction, int level) {
		this.name = name;
		this.shortname = shortname;
		this.desc = desc;
		this.syntax = syntax;
		this.action = action;
		this.nonadminaction = nonadminaction;
		this.level = level;
	}

}
