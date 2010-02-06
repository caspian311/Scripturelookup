package net.todd.bible.scripturelookup.server;

import java.util.List;

public interface ISearchResultToVerseConverter {

	List<Verse> convertToVerses(List<SearchResult> any);

}
