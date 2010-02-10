package net.todd.bible.scripturelookup.client;

public class DataLoadingPresenter {
	public DataLoadingPresenter(final IDataLoadingView dataManagementView,
			final IDataLoadingModel dataManagementModel) {
		dataManagementView.addReloadButtonListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showReloadingBusySignal();
				dataManagementModel.reloadData();
			}
		});

		dataManagementView.addDeleteButtonListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showDeletingBusySignal();
				dataManagementModel.deleteData();
			}
		});

		dataManagementModel.addFailureListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showErrorMessage(dataManagementModel.getErrorMessage());
			}
		});

		dataManagementModel.addDataDeletionListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showDeletionSuccessMessage();
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
