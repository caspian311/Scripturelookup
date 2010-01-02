package net.todd.bible.scripturelookup.client;

public class DataLoadingPresenter {
	public DataLoadingPresenter(final IDataLoadingView dataManagementView,
			final IDataLoadingModel dataManagementModel) {
		dataManagementView.addReloadButtonListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showBusySignal();
				dataManagementModel.reloadData();
			}
		});

		dataManagementView.addDeleteButtonListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showBusySignal();
				dataManagementModel.deleteData();
			}
		});

		dataManagementView.addReIndexButtonListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showBusySignal();
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
				dataManagementView.showSuccessMessage();
			}
		});
		
		dataManagementModel.addDataDeletionListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showSuccessMessage();
			}
		});

		dataManagementModel.addDataReloadedListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showSuccessMessage();
			}
		});
	}
}
