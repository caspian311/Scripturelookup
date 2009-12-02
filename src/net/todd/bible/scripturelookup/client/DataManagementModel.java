package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DataManagementModel implements IDataManagementModel {
	private final IDataManagementServiceAsync dataManagementService;

	public DataManagementModel(IDataManagementServiceAsync dataManagementService) {
		this.dataManagementService = dataManagementService;
	}

	@Override
	public void reloadData() {
		dataManagementService.reload("", new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(String result) {
			}
		});
	}
}
