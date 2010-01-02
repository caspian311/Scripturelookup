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

		dataManagementView.addReIndexButtonListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showIndexingBusySignal();
				dataManagementModel.createIndex();
			}
		});
		
		dataManagementModel.addFailureListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showErrorMessage(dataManagementModel.getErrorMessage());
			}
		});

		dataManagementModel.addIndexCreatedListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showIndexSuccessMessage();
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
