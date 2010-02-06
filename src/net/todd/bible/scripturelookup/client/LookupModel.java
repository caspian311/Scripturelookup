package net.todd.bible.scripturelookup.client;

import java.util.List;

public class LookupModel implements ILookupModel {
	private final ListenerManager noResultsReturnedListenerManager = new ListenerManager();
	private final ListenerManager resultsReturnedListenerManager = new ListenerManager();
	private final ListenerManager failureListenerManager = new ListenerManager();
	private String queryType;
	private final IServiceCaller serviceCaller;
	private List<Verse> returnResults;

	public LookupModel(final IServiceCaller serviceCaller, final IResultsParser parser) {
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
				if (returnResults.isEmpty()) {
					noResultsReturnedListenerManager.notifyListeners();
				} else {
					resultsReturnedListenerManager.notifyListeners();
				}
			}
		});
	}

	@Override
	public void queryServer(String query) {
		serviceCaller.callService(queryType, query);
	}

	@Override
	public List<Verse> searchResults() {
		return returnResults;
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
}
