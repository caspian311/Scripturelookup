package net.todd.bible.scripturelookup.client.parsers;

import java.util.List;

import net.todd.bible.scripturelookup.client.SearchResultsMetaData;
import net.todd.bible.scripturelookup.client.Verse;

public class ParsedResults {
	private final List<Verse> referenceList;

	public ParsedResults(List<Verse> referenceList) {
		this.referenceList = referenceList;
	}

	public List<Verse> getReferenceList() {
		return referenceList;
	}

	public SearchResultsMetaData getMetaData() {
		return new SearchResultsMetaData(referenceList.size());
	}
}
