package net.todd.bible.scripturelookup.server;

import java.io.InputStream;

public interface IDataFileStreamer {
	InputStream getStreamForFile(String dataFile);
}
