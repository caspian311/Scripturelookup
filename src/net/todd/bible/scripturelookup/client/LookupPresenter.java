package net.todd.bible.scripturelookup.client;

public class LookupPresenter {
	public LookupPresenter(final ILookupView lookupView, final ILookupModel lookupModel) {
		lookupView.addSubmissionListener(new IListener() {
			@Override
			public void handleEvent() {
				lookupView.disableSubmitButton();
				lookupView.showBusySignal();

				lookupModel.queryServer(lookupView.getQueryString());
			}
		});

		lookupModel.addFailureListener(new IListener() {
			@Override
			public void handleEvent() {
				lookupView.showErrorMessage(lookupModel.getErrorMessage());
				lookupView.enableSubmitButton();
			}
		});

		lookupModel.addResultsReturnedListener(new IListener() {
			@Override
			public void handleEvent() {
				lookupView.showVerses(lookupModel.searchResults());
				lookupView.enableSubmitButton();
			}
		});
		
		lookupModel.addNoResultsReturnedListener(new IListener() {
			@Override
			public void handleEvent() {
				lookupView.showNoResultsMessage();
			}
		});
		
		lookupView.addQueryTypeChangeListener(new IListener() {
			@Override
			public void handleEvent() {
				lookupModel.setQueryType(lookupView.getQueryType());
			}
		});
	}
}