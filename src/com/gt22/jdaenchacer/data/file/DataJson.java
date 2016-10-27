package com.gt22.jdaenchacer.data.file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.gt22.jdaenchacer.data.tags.DataBase;
import com.gt22.jdaenchacer.data.tags.DataStorage;

public class DataJson
{
	public static void write(File f, DataBase base) throws IOException
	{
		FileWriter w = new FileWriter(f);
		w.write(new GsonBuilder().setPrettyPrinting().create().toJson(base.toJson()));
		w.close();
	}
	
	public static DataStorage load(File f) throws IOException
	{
		FileReader r = new FileReader(f);
		DataStorage ret = DataBase.fromJson(new JsonParser().parse(r).getAsJsonObject());
		r.close();
		return ret;
	}
}
