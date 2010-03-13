package net.todd.bible.scripturelookup.client.view;

import java.util.List;

import net.todd.bible.scripturelookup.client.IListener;
import net.todd.bible.scripturelookup.client.SearchResultsMetaData;
import net.todd.bible.scripturelookup.client.Verse;

public interface ILookupView {
	void addSubmissionListener(IListener listener);
	
	void showBusySignal();
	
	void showErrorMessage(String errorMessage);
	
	void showNoResultsMessage();
	
	void showVerses(List<Verse> verses);
	
	void disableSubmitButton();

	void enableSubmitButton();
	
	String getQueryString();
	
	void addQueryTypeChangeListener(IListener listener);

	String getQueryType();
	
	void showMetaData(SearchResultsMetaData metaData);

	void clearResponseLabel();

	void clearResults();
}
