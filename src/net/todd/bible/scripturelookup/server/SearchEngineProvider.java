package net.todd.bible.scripturelookup.server;

public class SearchEngineProvider {
	private static ISearchEngine searchEngine = new SearchEngine(BibleDaoProvider.getBibleDao());

	public static ISearchEngine getSearchEngine() {
		return searchEngine;
	}
}
