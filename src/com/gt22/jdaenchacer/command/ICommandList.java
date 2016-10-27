package com.gt22.jdaenchacer.command;

import java.util.List;
import com.gt22.jdaenchacer.command.Command.CommandAction;

public interface ICommandList
{
	public List<Command> getCommands();
	
	public default Command createCommand(String name, String shortname, String desc, String syntax, CommandAction action, CommandAction nonadminaction, int level)
	{
		return new Command(name, shortname, desc, syntax, action, nonadminaction, level);
	}
	
	public default Command createCommand(String name, String shortname, String desc, String syntax, CommandAction action)
	{
		return createCommand(name, shortname, desc, syntax, action, null, 0);
	}
	
}
