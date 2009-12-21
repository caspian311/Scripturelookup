package net.todd.bible.scripturelookup.server;

import java.util.ArrayList;
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
	public List<SearchResult> search(String queryString) {
		return new ArrayList<SearchResult>();
	}
}
