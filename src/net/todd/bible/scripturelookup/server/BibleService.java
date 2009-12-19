package net.todd.bible.scripturelookup.server;

import java.util.List;

// XXX This class seems unnecessary
public class BibleService implements IBibleService {
	private final ISearchEngine searchEngine;

	public BibleService(ISearchEngine searchEngine) {
		this.searchEngine = searchEngine;
	}
	
	@Override
	public List<Verse> search(String searchText) {
		return searchEngine.search(searchText);
	}
}
