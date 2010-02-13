package net.todd.bible.scripturelookup.client;

public class DataLoadingPresenter {
	public DataLoadingPresenter(final IDataManagementView dataManagementView,
			final IDataLoadingModel dataManagementModel) {
		dataManagementView.addReloadButtonListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showReloadingBusySignal();
				dataManagementModel.reloadData();
			}
		});

		dataManagementModel.addFailureListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showErrorMessage(dataManagementModel.getErrorMessage());
			}
		});

		dataManagementModel.addDataReloadedListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showReloadSuccessMessage();
			}
		});
	}
}
