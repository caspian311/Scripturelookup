package net.todd.bible.scripturelookup.client;

import java.util.List;

public class LookupModel implements ILookupModel {
	private final ListenerManager noResultsReturnedListenerManager = new ListenerManager();
	private final ListenerManager resultsReturnedListenerManager = new ListenerManager();
	private final ListenerManager failureListenerManager = new ListenerManager();
	private String queryType;
	private final ILookupServiceCaller serviceCaller;
	private ParsedResults returnResults;

	public LookupModel(final ILookupServiceCaller serviceCaller, final IResultsParser parser) {
		this.serviceCaller = serviceCaller;
		serviceCaller.addFailureListener(new IListener() {
			@Override
			public void handleEvent() {
				failureListenerManager.notifyListeners();
			}
		});

		serviceCaller.addSuccessListener(new IListener() {
			@Override
			public void handleEvent() {
				returnResults = parser.parse(serviceCaller.getReturnValue());
				if (areResultsEmpty(returnResults)) {
					noResultsReturnedListenerManager.notifyListeners();
				} else {
					resultsReturnedListenerManager.notifyListeners();
				}
			}
		});
	}
	
	private boolean areResultsEmpty(ParsedResults returnResults) {
		if (returnResults == null || returnResults.getReferenceList() == null) {
			return true;
		}

		return returnResults.getReferenceList().isEmpty();
	}

	@Override
	public void queryServer(String query) {
		serviceCaller.callService(queryType, query);
	}

	@Override
	public List<Verse> searchResults() {
		return returnResults.getReferenceList();
	}

	@Override
	public String getErrorMessage() {
		return serviceCaller.getException().getMessage();
	}

	@Override
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	@Override
	public void addNoResultsReturnedListener(IListener listener) {
		noResultsReturnedListenerManager.addListener(listener);
	}

	public void addResultsReturnedListener(IListener listener) {
		resultsReturnedListenerManager.addListener(listener);
	}

	public void addFailureListener(IListener listener) {
		failureListenerManager.addListener(listener);
	}

	@Override
	public SearchResultsMetaData getSearchResultsMetaData() {
		return returnResults.getMetaData();
	}
}
