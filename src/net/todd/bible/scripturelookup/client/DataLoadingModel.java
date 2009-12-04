package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DataLoadingModel implements IDataLoadingModel {
	private final ListenerManager successListenerManager = new ListenerManager();
	private final ListenerManager failureListenerManager = new ListenerManager();

	private final IDataLoadingServiceAsync dataManagementService;
	private final IIndexServiceAsync indexService;
	private String errorMessage;

	public DataLoadingModel(IDataLoadingServiceAsync dataManagementService,
			IIndexServiceAsync indexService) {
		this.dataManagementService = dataManagementService;
		this.indexService = indexService;
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
