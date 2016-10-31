package com.gt22.jdaenchacer.data;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import com.gt22.jdaenchacer.data.file.DataJson;
import com.gt22.jdaenchacer.data.tags.DataBase;
import com.gt22.jdaenchacer.data.tags.DataStorage;

public abstract class DataWrapperBase<T> implements IDataStorage {
	public final T base;
	protected DataStorage meta;

	public DataWrapperBase(T base) throws IOException {
		this.base = base;
		loadFromFile();
	}

	@Override
	public void setData(String key, DataBase<?> data) {
		meta.setData(key, data);
		saveToFile();
	}

	@Override
	public DataBase<?> getData(String key) {
		return meta.getData(key);
	}

	@Override
	public boolean hasData(String key) {
		return meta.hasData(key);
	}

	@Override
	public void removeData(String key) {
		meta.removeData(key);
		saveToFile();
	}

	protected void loadFromFile() {
		File file = getFile();
		if (file.exists()) {
			try {
				meta = DataJson.load(file);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		} else {
			meta = new DataStorage();
		}
	}

	protected void saveToFile() {
		try {
			DataJson.write(getFile(), meta);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public abstract File getFile();
}
