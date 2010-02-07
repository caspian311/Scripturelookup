package net.todd.bible.scripturelookup.server;

public class DataLoaderProvider {
	private static final IDataLoader dataLoader = new DataLoader(PMF.get());

	public static IDataLoader getDataLoader() {
		return dataLoader;
	}
}
