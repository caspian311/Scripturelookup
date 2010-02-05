package net.todd.bible.scripturelookup.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class LookupModel implements ILookupModel {
	private final List<Verse> searchResults = new ArrayList<Verse>();

	private final ListenerManager failureListenerManager = new ListenerManager();
	private final ListenerManager resultsReturnedListenerManager = new ListenerManager();
	private final ListenerManager noResultsListenerManager = new ListenerManager();

	private final ILookupServiceAsync lookupService;

	private String errorMessage;

	private final IResultsParser parser;

	public LookupModel(ILookupServiceAsync lookupService, IResultsParser parser) {
		this.lookupService = lookupService;
		this.parser = parser;
	}

	public void queryServer(String query) {
		lookupService.lookup(query, new AsyncCallback<String>() {
			public void onFailure(Throwable error) {
				errorMessage = error.getMessage();
				failureListenerManager.notifyListeners();
			}

			public void onSuccess(String response) {
				popuplateSearchResults(response);
				if (searchResults.size() != 0) {
					resultsReturnedListenerManager.notifyListeners();
				} else {
					noResultsListenerManager.notifyListeners();
				}
			}
		});
	}

	private void popuplateSearchResults(String response) {
		searchResults.clear();

		if (response != null && response.length() != 0) {
			searchResults.addAll(parser.parse(response));
		}
	}

	public void addResultsReturnedListener(IListener listener) {
		resultsReturnedListenerManager.addListener(listener);
	}

	public void addFailureListener(IListener listener) {
		failureListenerManager.addListener(listener);
	}

	public List<Verse> searchResults() {
		return searchResults;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void addNoResultsReturnedListener(IListener listener) {
		noResultsListenerManager.addListener(listener);
	}

	@Override
	public void setQueryType(String queryType) {
		// TODO implement this
	}
}
