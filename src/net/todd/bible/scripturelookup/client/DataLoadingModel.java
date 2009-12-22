package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DataLoadingModel implements IDataLoadingModel {
	private final ListenerManager reloadListenerManager = new ListenerManager();
	private final ListenerManager failureListenerManager = new ListenerManager();

	private final IDataLoadingServiceAsync dataManagementService;
	private String errorMessage;

	public DataLoadingModel(IDataLoadingServiceAsync dataManagementService) {
		this.dataManagementService = dataManagementService;
	}

	@Override
	public void reloadData() {
		dataManagementService.reload("", new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				errorMessage = caught.getMessage();
				failureListenerManager.notifyListeners();
			}

			@Override
			public void onSuccess(String result) {
				reloadListenerManager.notifyListeners();
			}
		});
	}

	@Override
	public void addDataReloadedListener(IListener listener) {
		reloadListenerManager.addListener(listener);
	}

	@Override
	public void addFailureListener(IListener listener) {
		failureListenerManager.addListener(listener);
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
