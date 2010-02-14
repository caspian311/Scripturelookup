package net.todd.bible.scripturelookup.client;

import java.util.Arrays;
import java.util.List;

public class FileProvider implements IFileProvider {
	private static final List<String> FILES_TO_LOAD = Arrays.asList("/data1.txt", "/data2.txt",
			"/data3.txt", "/data4.txt", "/data5.txt", "/data6.txt", "/data7.txt", "/data8.txt",
			"/data9.txt", "/data10.txt");
	
	@Override
	public List<String> filesToLoad() {
		return FILES_TO_LOAD;
	}
}
