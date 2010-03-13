package net.todd.bible.scripturelookup.server;

import java.util.ArrayList;
import java.util.List;

import net.todd.bible.scripturelookup.server.data.Verse;

public class SearchResultToVerseConverter implements ISearchResultToVerseConverter {
	@Override
	public List<Verse> convertToVerses(List<SearchResult> searchResults) {
		List<Verse> verses = new ArrayList<Verse>();
		if (searchResults != null) {
			for (SearchResult result : searchResults) {
				verses.add(new Verse(result.getBook(), result.getChapter(), result.getVerse(),
						result.getText()));
			}
		}
		return verses;
	}
}
