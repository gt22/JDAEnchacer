package com.gt22.jdaenchacer.getters;

import java.util.List;
import java.util.stream.Collectors;

import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.VoiceChannel;

public class Getters
{
	public static User getUser( String name, String discriminator, JDA bot)
	{
		return bot.getUsersByName(name).stream().filter((user) -> user.getDiscriminator().equals(discriminator)).findFirst().orElse(null);
	}
	
	public static Wrapper<User> getUser(String from, JDA bot)
	{
		User usr = bot.getUserById(from);
		if (usr == null)
		{
			if (from.contains("#") && !from.startsWith("#"))
			{
				String[] str = from.split("#");
				usr = getUser(str[0], str[1], bot);
				if (usr != null)
				{
					return new Wrapper<User>(usr);
				}
			}
			else
			{
				if (from.startsWith("@"))
				{
					from = from.substring(1);
				}
				String f = from;
				if(f.startsWith("#"))
				{
					return new Wrapper<User>(bot.getUsers().parallelStream().filter(user -> user.getDiscriminator().equals(f.substring(1))).toArray(User[]::new));
				}
				else
				{
					List<User> users = bot.getUsersByName(from);
					return new Wrapper<User>(users.toArray(new User[users.size()]));
				}
			}
		}
		else
		{
			return new Wrapper<User>(usr);
		}
		return new Wrapper<User>();
	}
	
	public static Wrapper<TextChannel> getTextChannel(String from, Guild guild, JDA bot)
	{
		TextChannel chan = bot.getTextChannelById(from);
		if(chan == null)
		{
			String t = from.startsWith("#") ? from.substring(1) : from;
			List<TextChannel> chans = guild == null ? bot.getTextChannelsByName(t) : guild.getTextChannels().parallelStream().filter(c -> c.getName().equals(t)).collect(Collectors.toList());
			return new Wrapper<TextChannel>(chans.toArray(new TextChannel[chans.size()]));
		}
		else
		{
			return new Wrapper<TextChannel>(chan);
		}
	}
	public static Wrapper<VoiceChannel> getVoiceChannel(String from, Guild guild, JDA bot)
	{
		VoiceChannel chan = bot.getVoiceChannelById(from);
		if(chan == null)
		{
			String t = from.startsWith("#") ? from.substring(1) : from;
			List<VoiceChannel> chans = guild == null ? bot.getVoiceChannelByName(t) : guild.getVoiceChannels().parallelStream().filter(c -> c.getName().equals(t)).collect(Collectors.toList());
			return new Wrapper<VoiceChannel>(chans.toArray(new VoiceChannel[chans.size()]));
		}
		else
		{
			return new Wrapper<VoiceChannel>(chan);
		}
	}
	
	public static Wrapper<Guild> getGuild(String from, JDA bot)
	{
		Guild guild = bot.getGuildById(from);
		if(guild == null)
		{
			List<Guild> guilds = bot.getGuildsByName(from);
			return new Wrapper<Guild>(guilds.toArray(new Guild[guilds.size()]));
		}
		return new Wrapper<Guild>(guild);
	}
}
