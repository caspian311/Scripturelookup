package net.todd.bible.scripturelookup.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileProvider implements IFileProvider {
	@Override
	public List<String> filesToLoad() {
		List<String> filesToLoad = new ArrayList<String>();
		for (int i = 1; i <= 312; i++) {
			filesToLoad.add("data" + i + ".txt");
		}
		return Collections.unmodifiableList(filesToLoad);
	}
}
