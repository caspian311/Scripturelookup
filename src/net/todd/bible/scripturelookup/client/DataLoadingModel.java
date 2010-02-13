package net.todd.bible.scripturelookup.client;


public class DataLoadingModel implements IDataLoadingModel {
	private final ListenerManager failureListenerManager = new ListenerManager();

	private final IDataLoadingServiceCaller dataLoadingServiceCaller;

	protected String errorMessage;
	
	public DataLoadingModel(final IDataLoadingServiceCaller dataLoadingServiceCaller,
			IFileProvider fileProvider) {
		this.dataLoadingServiceCaller = dataLoadingServiceCaller;
		
		dataLoadingServiceCaller.addFailureListener(new IListener() {
			@Override
			public void handleEvent() {
				Throwable exception = dataLoadingServiceCaller.getException();
				if (exception != null) {
					errorMessage = exception.getMessage();
				}
				failureListenerManager.notifyListeners();
			}
		});

		dataLoadingServiceCaller.addSuccessListener(new IListener() {
			@Override
			public void handleEvent() {
			}
		});
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

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
