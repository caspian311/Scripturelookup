package net.todd.bible.scripturelookup.client;

import java.util.List;

public interface ILookupModel {
	void queryServer(String query);

	List<Verse> searchResults();
	
	void addResultsReturnedListener(IListener listener);
	
	void addFailureListener(IListener listener);

	String getErrorMessage();

	void addNoResultsReturnedListener(IListener listener);

	void setQueryType(String queryType);

	SearchResultsMetaData getSearchResultsMetaData();
}
