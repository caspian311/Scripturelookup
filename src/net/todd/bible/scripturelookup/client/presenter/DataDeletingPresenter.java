package net.todd.bible.scripturelookup.client.presenter;

import net.todd.bible.scripturelookup.client.IListener;
import net.todd.bible.scripturelookup.client.model.IDataDeletingModel;
import net.todd.bible.scripturelookup.client.view.IDataManagementView;

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
