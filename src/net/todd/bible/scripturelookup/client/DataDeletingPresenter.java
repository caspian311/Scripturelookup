package net.todd.bible.scripturelookup.client;

public class DataDeletingPresenter {
	public DataDeletingPresenter(final IDataManagementView view, final IDataDeletingModel model) {
		view.addDeleteButtonListener(new IListener() {
			@Override
			public void handleEvent() {
				view.showDeletingBusySignal();
				model.deleteData();
			}
		});

		model.addDataDeletionListener(new IListener() {
			@Override
			public void handleEvent() {
				view.showDeletionSuccessMessage();
			}
		});
	}
}
