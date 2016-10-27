package com.gt22.jdaenchacer.data;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import net.dv8tion.jda.entities.User;

public class AdvUser extends DataWrapperBase<User>
{
	private static final HashMap<String, AdvUser> users = new HashMap<String, AdvUser>();

	private AdvUser(User user) throws IOException
	{
		super(user);
	}
	
	@Override
	public File getFile()
	{
		return new File("internal/users/" + base.getId() + ".json");
	}

	public static void saveUsers()
	{
		users.values().forEach(u ->
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
	
	public void setLevel(int level)
	{
		if (level == 0)
		{
			removeData("lvl");
		}
		else
		{
			setInt("lvl", level);
		}
	}

	public int getLevel()
	{
		return getInt("lvl");
	}

	public static AdvUser of(User usr)
	{
		if(!users.containsKey(usr.getId()))
		{
			try
			{
				users.put(usr.getId(), new AdvUser(usr));
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		return users.get(usr.getId());
	}
	
	public static Collection<AdvUser> getUsers()
	{
		return users.values();
	}
}
