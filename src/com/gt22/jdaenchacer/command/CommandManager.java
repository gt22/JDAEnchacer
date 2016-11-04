package com.gt22.jdaenchacer.command;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;

public class CommandManager
{
	public static interface CanUseCommand
	{
		public boolean canUse(User user, Command command);
	}
	
	public static interface OnCommandUse
	{
		public void onUse(Command c, String[] args, User sender);
	}

	private final CanUseCommand canUse;
	private final OnCommandUse onCommandUse;
	private final boolean storeLists;
	private final String mention;
	private final List<ICommandList> commandLists;
	private final List<Command> commands = new ArrayList<Command>();
	public CommandManager(CanUseCommand canUse, String mention, OnCommandUse onCommandUse)
	{
		this(canUse, mention, true, onCommandUse);
	}

	public CommandManager(CanUseCommand canUse, String mention, boolean storeLists, OnCommandUse onCommandUse)
	{
		this.canUse = canUse;
		this.mention = mention;
		this.storeLists = storeLists;
		this.commandLists = storeLists ? new ArrayList<ICommandList>() : null;
		this.onCommandUse = onCommandUse;
	}

	public void addCommandList(ICommandList list)
	{
		if (storeLists)
		{
			commandLists.add(list);
		}
		commands.addAll(list.getCommands());
	}

	public void addCommand(Command cm)
	{
		commands.add(cm);
	}

	public Command getCommandByName(String name)
	{
		for (Command cm : commands)
		{
			if (cm.name.equals(name) || cm.shortname.equals(name))
			{
				return cm;
			}
		}
		return null;
	}

	public List<Command> getCommands()
	{
		return commands;
	}

	public void clear()
	{
		commands.clear();
	}

	private void init()
	{
		if(storeLists)
		{
			commandLists.forEach(list -> commands.addAll(list.getCommands()));
		}
		else
		{
			throw new UnsupportedOperationException("Init cannot be used while storing lists is disabled");
		}
	}

	public void reset()
	{
		if (storeLists)
		{
			clear();
			init();
		}
		else
		{
			throw new UnsupportedOperationException("Reset cannot be used while storing lists is disabled");
		}
	}

	public static enum ExecuteResult
	{
		NO_SUCH_COMMNAD, SUCCES, CANNOT_USE;
	}

	public ExecuteResult tryExecuteCommand(String text, Message msg, User usr, Guild guild)
	{
		String[] t = text.substring(mention.length()).replaceAll("\\s{2,}", " ").split("\\s", 2);
		String name = t[0].toLowerCase();
		String[] args = t.length == 1 ? new String[]{} : t[1].split("\\s");
		Command cm = getCommandByName(name);
		if (cm == null)
		{
			return ExecuteResult.NO_SUCH_COMMNAD;
		}
		else
		{
			onCommandUse.onUse(cm, args, usr);
			if (canUse.canUse(usr, cm))
			{
				cm.action.performCommand(msg, args, guild);
				return ExecuteResult.SUCCES;
			}
			else
			{
				if (cm.nonadminaction != null)
				{
					cm.nonadminaction.performCommand(msg, args, guild);
				}
				return ExecuteResult.CANNOT_USE;
			}
		}
	}
}
