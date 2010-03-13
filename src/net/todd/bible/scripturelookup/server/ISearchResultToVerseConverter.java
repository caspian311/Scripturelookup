package net.todd.bible.scripturelookup.server;

import java.util.List;

import net.todd.bible.scripturelookup.server.data.Verse;

public interface ISearchResultToVerseConverter {

	List<Verse> convertToVerses(List<SearchResult> any);

}
