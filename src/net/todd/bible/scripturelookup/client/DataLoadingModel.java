package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DataLoadingModel implements IDataLoadingModel {
	private final ListenerManager reloadListenerManager = new ListenerManager();
	private final ListenerManager failureListenerManager = new ListenerManager();
	private final ListenerManager indexBuiltListenerManager = new ListenerManager();

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
				reloadListenerManager.notifyListeners();
			}
		});
	}

	@Override
	public void addIndexBuiltListener(IListener listener) {
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

	@Override
	public void rebuildIndex() {
		indexService.rebuildIndex(null, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				errorMessage = caught.getMessage();
				failureListenerManager.notifyListeners();
			}

			@Override
			public void onSuccess(String result) {
				indexBuiltListenerManager.notifyListeners();
			}
		});
	}

	@Override
	public void addIndexBuildSuccessListener(IListener listener) {
		indexBuiltListenerManager.addListener(listener);
	}
}
