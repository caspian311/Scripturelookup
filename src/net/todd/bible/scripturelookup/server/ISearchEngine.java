package net.todd.bible.scripturelookup.server;

import java.util.List;

public interface ISearchEngine {
	void createIndex();

	List<SearchResult> search(String query);
}
