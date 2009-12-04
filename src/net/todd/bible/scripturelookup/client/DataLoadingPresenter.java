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
		
		dataManagementModel.addFailureListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showErrorMessage(dataManagementModel.getErrorMessage());
			}
		});
		
		dataManagementModel.addIndexBuiltListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showSuccessMessage();
			}
		});
	}
}
