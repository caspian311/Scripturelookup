package net.todd.bible.scripturelookup.client;

public class DataLoadingModel implements IDataLoadingModel {
	private final ListenerManager failureListenerManager = new ListenerManager();
	private final ListenerManager dataDeletedListenerManager = new ListenerManager();
	
	private final IDataDeletingServiceCaller dataDeletingServiceCaller;
	private final IDataLoadingServiceCaller dataLoadingServiceCaller;
	
	protected String errorMessage;


	public DataLoadingModel(final IDataDeletingServiceCaller dataDeletingServiceCaller,
			IDataLoadingServiceCaller dataLoadingServiceCaller) {
		this.dataDeletingServiceCaller = dataDeletingServiceCaller;
		this.dataLoadingServiceCaller = dataLoadingServiceCaller;
		dataDeletingServiceCaller.addFailureListener(new IListener() {
			@Override
			public void handleEvent() {
				Throwable exception = dataDeletingServiceCaller.getException();
				if (exception != null) {
					errorMessage = exception.getMessage();
				}
				failureListenerManager.notifyListeners();
			}
		});

		dataDeletingServiceCaller.addSuccessListener(new IListener() {
			@Override
			public void handleEvent() {
				dataDeletedListenerManager.notifyListeners();
			}
		});
		
		dataLoadingServiceCaller.addFailureListener(new IListener() {
			@Override
			public void handleEvent() {
			}
		});

		dataLoadingServiceCaller.addSuccessListener(new IListener() {
			@Override
			public void handleEvent() {
			}
		});
	}

	@Override
	public void deleteData() {
		dataDeletingServiceCaller.callService();
	}

	@Override
	public void reloadData() {
		dataLoadingServiceCaller.callService("");
	}

	@Override
	public void addDataReloadedListener(IListener listener) {
	}

	@Override
	public void addFailureListener(IListener listener) {
		failureListenerManager.addListener(listener);
	}

	public void addDataDeletionListener(IListener listener) {
		dataDeletedListenerManager.addListener(listener);
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
