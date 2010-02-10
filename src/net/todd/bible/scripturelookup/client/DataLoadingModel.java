package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DataLoadingModel implements IDataLoadingModel {
	private final ListenerManager failureListenerManager = new ListenerManager();
	private final ListenerManager loadListenerManager = new ListenerManager();
	private final ListenerManager deleteListenerManager = new ListenerManager();

	private final IDataLoadingServiceAsync dataManagementService;
	private final IDataDeletingServiceAsync dataDeletingService;
	
	private String errorMessage;

	public DataLoadingModel(IDataDeletingServiceAsync dataDeletingService,
			IDataLoadingServiceAsync dataManagementService) {
		this.dataManagementService = dataManagementService;
		this.dataDeletingService = dataDeletingService;
	}

	@Override
	public void deleteData() {
		dataDeletingService.deleteAllData(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				errorMessage = caught.getMessage();
				failureListenerManager.notifyListeners();
			}

			@Override
			public void onSuccess(String result) {
				deleteListenerManager.notifyListeners();
			}
		});
	}
	
	@Override
	public void reloadData() {
		dataManagementService.loadAllData(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				errorMessage = caught.getMessage();
				failureListenerManager.notifyListeners();
			}

			@Override
			public void onSuccess(String result) {
				loadListenerManager.notifyListeners();
			}
		});
	}

	@Override
	public void addDataReloadedListener(IListener listener) {
		loadListenerManager.addListener(listener);
	}

	@Override
	public void addFailureListener(IListener listener) {
		failureListenerManager.addListener(listener);
	}
	
	public void addDataDeletionListener(IListener listener) {
		deleteListenerManager.addListener(listener);
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
