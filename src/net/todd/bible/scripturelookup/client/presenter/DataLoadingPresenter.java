package net.todd.bible.scripturelookup.client.presenter;

import net.todd.bible.scripturelookup.client.IListener;
import net.todd.bible.scripturelookup.client.model.IDataLoadingModel;
import net.todd.bible.scripturelookup.client.view.IDataManagementView;

public class DataLoadingPresenter {
	public DataLoadingPresenter(final IDataManagementView dataManagementView,
			final IDataLoadingModel dataLoadingModel) {
		dataManagementView.addReloadButtonListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showReloadingBusySignal();
				dataLoadingModel.reloadData();
			}
		});

		dataLoadingModel.addFailureListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showErrorMessage(dataLoadingModel.getErrorMessage());
			}
		});

		dataLoadingModel.addDataReloadedListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showReloadSuccessMessage();
			}
		});

		dataLoadingModel.addProgressListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.updatePercentComplete(dataLoadingModel.getPercentComplete());
			}
		});
	}
}
