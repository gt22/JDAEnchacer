package com.gt22.jdaenchacer.data;

import java.io.File;
import java.io.IOException;
import com.gt22.jdaenchacer.data.file.DataJson;
import com.gt22.jdaenchacer.data.tags.DataBase;
import com.gt22.jdaenchacer.data.tags.DataStorage;

public abstract class DataWrapperBase<T> implements IDataStorage
{
	public final T base;
	protected DataStorage meta;
	public DataWrapperBase(T base) throws IOException
	{
		this.base = base;
		loadFromFile();
	}
	
	@Override
	public void setData(String key, DataBase data) throws RuntimeException
	{
		meta.setData(key, data);
		try
		{
			saveToFile();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public DataBase getData(String key)
	{
		return meta.getData(key);
	}

	@Override
	public boolean hasData(String key)
	{
		return meta.hasData(key);
	}
	
	@Override
	public void removeData(String key) throws RuntimeException
	{
		meta.removeData(key);
		try
		{
			saveToFile();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	protected void loadFromFile() throws IOException
	{
		File file = getFile();
		if(file.exists())
		{
			meta = DataJson.load(file);
		}
		else
		{
			meta = new DataStorage();
		}
	}
	
	protected void saveToFile() throws IOException
	{
		DataJson.write(getFile(), meta);
	}
	
	public abstract File getFile();
}
