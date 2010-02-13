package net.todd.bible.scripturelookup.client;

public class DataDeletingModel implements IDataDeletingModel {
	private final ListenerManager failureListenerManager = new ListenerManager();
	private final ListenerManager dataDeletedListenerManager = new ListenerManager();

	private final IDataDeletingServiceCaller dataDeletingServiceCaller;
	
	protected String errorMessage;
	
	public DataDeletingModel(final IDataDeletingServiceCaller dataDeletingServiceCaller) {
		this.dataDeletingServiceCaller = dataDeletingServiceCaller;
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
	}
	
	@Override
	public void deleteData() {
		dataDeletingServiceCaller.callService();
	}
	
	@Override
	public void addFailureListener(IListener listener) {
		failureListenerManager.addListener(listener);
	}

	@Override
	public void addDataDeletionListener(IListener listener) {
		dataDeletedListenerManager.addListener(listener);
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
