package net.todd.bible.scripturelookup.server;

import net.todd.bible.scripturelookup.server.data.PMF;

public class DataLoaderProvider {
	private static final IDataLoader dataLoader = new DataLoader(PMF.get());

	public static IDataLoader getDataLoader() {
		return dataLoader;
	}
}
