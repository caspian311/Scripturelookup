package net.todd.bible.scripturelookup.server.search;

import net.todd.bible.scripturelookup.server.ISearchEngine;

public interface IBibleSearchFactory {
	ISearchEngine getSearchEngine(String queryType);
}
