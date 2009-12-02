package net.todd.bible.scripturelookup.client;

public class DataManagementPresenter {
	public DataManagementPresenter(final IDataManagementView dataManagementView,
			final IDataManagementModel dataManagementModel) {
		dataManagementView.addReloadButtonListener(new IListener() {
			@Override
			public void handleEvent() {
				dataManagementModel.reloadData();
			}
		});
	}
}
