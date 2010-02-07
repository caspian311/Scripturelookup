package net.todd.bible.scripturelookup.server;

import java.io.InputStream;

public interface IDataLoader {
	void loadData(InputStream inputStream);

	void deleteData();
}
