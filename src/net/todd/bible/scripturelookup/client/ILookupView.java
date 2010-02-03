package net.todd.bible.scripturelookup.client;

import java.util.List;

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
}
