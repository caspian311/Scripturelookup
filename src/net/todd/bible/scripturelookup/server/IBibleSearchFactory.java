package net.todd.bible.scripturelookup.server;

public interface IBibleSearchFactory {
	ISearchEngine getSearchEngine(String queryType);
}
