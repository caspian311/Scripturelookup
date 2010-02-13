package net.todd.bible.scripturelookup.server;

import java.io.InputStream;

public class DataFileStreamer implements IDataFileStreamer {
	@Override
	public InputStream getStreamForFile(String dataFile) {
		return getClass().getResourceAsStream(dataFile);
	}
}
