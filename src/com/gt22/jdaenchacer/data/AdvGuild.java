package com.gt22.jdaenchacer.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import com.gt22.jdaenchacer.data.tags.DataList;
import com.gt22.jdaenchacer.data.tags.DataStorage;
import com.gt22.jdaenchacer.data.tags.DataString;
import net.dv8tion.jda.entities.Guild;

public class AdvGuild extends DataWrapperBase<Guild>
{
	public static final HashMap<String, AdvGuild> guilds = new HashMap<>();
	private AdvGuild(Guild guild) throws IOException
	{
		super(guild);
		if(!meta.hasData("channels"))
		meta.setData("channels", createFilters());
	}
	
	private static DataStorage createFilters()
	{
		DataStorage channels = new DataStorage();
		channels.setData("blacklist", new DataList());
		channels.setData("whitelist", new DataList());
		channels.setData("botonly", new DataList());
		return channels;
	}
	
	public DataList getBlacklistedChannels()
	{
		return getTag("channels").getList("blacklist");
	}
	
	public void addBlacklistedChannel(String id)
	{
		getBlacklistedChannels().add(new DataString(id));
		saveToFile();
	}
	
	public void removeBlacklistedChannel(String id)
	{
		getBlacklistedChannels().remove(new DataString(id));
		saveToFile();
	}
	
	public boolean isBlacklistedChannel(String id)
	{
		return getBlacklistedChannels().contains(new DataString(id));
	}
	
	public boolean hasBlacklistedChannels()
	{
		return getBlacklistedChannels().size() != 0;
	}
	
	
	
	public DataList getWhitelistedChannels()
	{
		return getTag("channels").getList("whitelist");
	}
	
	public void addWhitelistedChannel(String id)
	{
		getWhitelistedChannels().add(new DataString(id));
		saveToFile();
	}
	
	public void removeWhitelistedChannel(String id)
	{
		getWhitelistedChannels().remove(new DataString(id));
		saveToFile();
	}
	
	public boolean isWhitelistedChannel(String id)
	{
		return getWhitelistedChannels().contains(new DataString(id));
	}
	
	public boolean hasWhitelistedChannels()
	{
		return getWhitelistedChannels().size() != 0;
	}
	
	
	
	public DataList getBotonlyChannels()
	{
		return getTag("channels").getList("botonly");
	}
	
	public void addBotonlyChannel(String id) 
	{
		getBotonlyChannels().add(new DataString(id));
		saveToFile();
	}
	
	public void removeBotonlyChannel(String id)
	{
		getBotonlyChannels().remove(new DataString(id));
		saveToFile();
	}
	
	public boolean isBotonlyChannel(String id)
	{
		return getBotonlyChannels().contains(new DataString(id));
	}
	
	public boolean hasBotonlyChannels()
	{
		return getBotonlyChannels().size() != 0;
	}
	
	
	
	@Override
	public File getFile()
	{
		return new File("internal/guilds/" + base.getId() + ".json");
	}
	
	public static void saveGuilds()
	{
		guilds.values().forEach(u ->
		{
			try
			{
				u.saveToFile();
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		});
	}
	
	public static AdvGuild of(Guild guild)
	{
		if(!guilds.containsKey(guild.getId()))
		{
			try
			{
				guilds.put(guild.getId(), new AdvGuild(guild));
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		return guilds.get(guild.getId());
	}
}
