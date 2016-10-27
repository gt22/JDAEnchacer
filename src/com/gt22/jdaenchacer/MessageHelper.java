package com.gt22.jdaenchacer;

import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

public class MessageHelper
{	
	public static void replyToUser(MessageChannel chan, User user, String reply)
	{
		chan.sendMessage(user.getAsMention() + " " + reply);
	}
	
	public static void reply(Message msg, String reply)
	{
		replyToUser(msg.getChannel(), msg.getAuthor(), reply);
	}
	
	public static void reply(Message msg, String reply, Object... data)
	{
		reply(msg, String.format(reply, data));
	}
}
