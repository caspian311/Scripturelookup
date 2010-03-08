package net.todd.bible.scripturelookup.client;

import java.util.List;

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
