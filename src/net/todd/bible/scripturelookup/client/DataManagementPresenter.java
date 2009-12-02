package net.todd.bible.scripturelookup.client;

public class DataManagementPresenter {
	public DataManagementPresenter(final IDataManagementView dataManagementView,
			final IDataManagementModel dataManagementModel) {
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
		
		dataManagementModel.addSuccessListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementView.showSuccessMessage();
			}
		});
	}
}
