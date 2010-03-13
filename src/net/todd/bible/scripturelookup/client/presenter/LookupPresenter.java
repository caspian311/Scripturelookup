package net.todd.bible.scripturelookup.client.presenter;

import net.todd.bible.scripturelookup.client.IListener;
import net.todd.bible.scripturelookup.client.model.ILookupModel;
import net.todd.bible.scripturelookup.client.view.ILookupView;

public class LookupPresenter {
	public LookupPresenter(final ILookupView lookupView, final ILookupModel lookupModel) {
		lookupView.addSubmissionListener(new IListener() {
			@Override
			public void handleEvent() {
				lookupView.disableSubmitButton();
				lookupView.showBusySignal();
				lookupModel.setQueryType(lookupView.getQueryType());

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
				lookupView.clearResponseLabel();
				lookupView.clearResults();
				lookupView.showMetaData(lookupModel.getSearchResultsMetaData());
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
	}
}