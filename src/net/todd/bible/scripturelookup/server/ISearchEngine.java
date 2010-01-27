package net.todd.bible.scripturelookup.server;

import java.util.List;

public interface ISearchEngine {
	List<SearchResult> search(String query);
}
