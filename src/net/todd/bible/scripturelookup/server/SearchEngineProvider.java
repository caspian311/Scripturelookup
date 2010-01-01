package net.todd.bible.scripturelookup.server;

public class SearchEngineProvider {
	private final static ISearchEngine searchEngine = new SearchEngine(BibleDaoProvider
			.getBibleDao());

	public static ISearchEngine getSearchEngine() {
		return searchEngine;
	}
}
