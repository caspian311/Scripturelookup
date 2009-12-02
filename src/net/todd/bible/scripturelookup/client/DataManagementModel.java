package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DataManagementModel implements IDataManagementModel {
	private final ListenerManager successListenerManager = new ListenerManager();
	private final ListenerManager failureListenerManager = new ListenerManager();
	
	private final IDataManagementServiceAsync dataManagementService;
	private String errorMessage;

	public DataManagementModel(IDataManagementServiceAsync dataManagementService) {
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
				successListenerManager.notifyListeners();
			}
		});
	}

	@Override
	public void addSuccessListener(IListener listener) {
		successListenerManager.addListener(listener);
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
