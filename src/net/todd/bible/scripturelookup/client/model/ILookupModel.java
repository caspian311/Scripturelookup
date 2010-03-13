package net.todd.bible.scripturelookup.client.model;

import java.util.List;

import net.todd.bible.scripturelookup.client.IListener;
import net.todd.bible.scripturelookup.client.SearchResultsMetaData;
import net.todd.bible.scripturelookup.client.Verse;

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
