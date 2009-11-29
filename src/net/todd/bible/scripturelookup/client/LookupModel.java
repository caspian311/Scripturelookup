package net.todd.bible.scripturelookup.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LookupModel implements ILookupModel {
	private final List<Verse> searchResults = new ArrayList<Verse>();
	
	private final ListenerManager failureListenerManager = new ListenerManager();
	private final ListenerManager resultsReturnedListenerManager = new ListenerManager();

	private final ILookupServiceAsync lookupService;

	private String errorMessage;

	public LookupModel(ILookupServiceAsync lookupService) {
		this.lookupService = lookupService;
	}

	public void queryServer(String query) {
		lookupService.lookup(query, new AsyncCallback<String>() {
			public void onFailure(Throwable error) {
				errorMessage = error.getMessage();
				failureListenerManager.notifyListeners();
			}

			public void onSuccess(String response) {
				popuplateSearchResults(response);

				resultsReturnedListenerManager.notifyListeners();
			}
		});
	}

	private void popuplateSearchResults(String response) {
		searchResults.clear();

		JSONValue parsedValue = JSONParser.parse(response);
		
		if (parsedValue.isArray() != null) {
			JSONArray responseArray = parsedValue.isArray();
			
			for (int i = 0; i < responseArray.size(); i++) {
				JSONValue value = responseArray.get(i);
				searchResults.add(new Verse(value.isObject()));
			}
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
}
