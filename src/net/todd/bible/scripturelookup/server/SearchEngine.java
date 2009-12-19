package net.todd.bible.scripturelookup.server;

import java.util.List;

public class SearchEngine implements ISearchEngine {
	public SearchEngine(IBibleDao bibleDao) {
	}

	@Override
	public void createIndex() {
	}

	@Override
	public void deleteExistingIndex() {
	}

	@Override
	public List<Verse> search(String query) {
		return null;
	}
}
