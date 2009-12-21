package net.todd.bible.scripturelookup.server;

import java.util.List;

public interface ISearchEngine {
	void deleteExistingIndex();

	void createIndex();

	List<SearchResult> search(String query);
}
