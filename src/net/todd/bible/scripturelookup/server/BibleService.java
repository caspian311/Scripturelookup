package net.todd.bible.scripturelookup.server;

import java.util.ArrayList;
import java.util.List;

// XXX This class seems unnecessary
public class BibleService implements IBibleService {
	private final ISearchEngine searchEngine;

	public BibleService(ISearchEngine searchEngine) {
		this.searchEngine = searchEngine;
	}
	
	@Override
	public List<Verse> search(String searchText) {
		return convertToVerses(searchEngine.search(searchText));
	}

	private List<Verse> convertToVerses(List<SearchResult> results) {
		List<Verse> verses = new ArrayList<Verse>();
		
		for (SearchResult result : results) {
			verses.add(new Verse("", "", "", result.getText()));
		}
		
		return verses;
	}
}
