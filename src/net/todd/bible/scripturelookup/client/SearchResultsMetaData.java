package net.todd.bible.scripturelookup.client;

public class SearchResultsMetaData {
	private final int totalSize;

	public SearchResultsMetaData(int totalSize) {
		this.totalSize = totalSize;
	}

	@Override
	public String toString() {
		return "Total results: " + totalSize;
	}
}
