package com.gt22.jdaenchacer.data.tags;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class DataBool extends DataBase<Boolean>
{

	public DataBool(Boolean value)
	{
		super(value);
	}
	
	@Override
	public JsonElement toJson()
	{
		return new JsonPrimitive(value);
	}

	
}
