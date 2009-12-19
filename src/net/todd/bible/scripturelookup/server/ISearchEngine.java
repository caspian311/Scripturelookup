package net.todd.bible.scripturelookup.server;

import java.util.List;

public interface ISearchEngine {
	void deleteExistingIndex();

	void createIndex();

	List<Verse> search(String query);
}
