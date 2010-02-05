package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ServiceCaller implements IServiceCaller {
	private final ILookupServiceAsync service;
	
	private final ListenerManager failureListenerManager = new ListenerManager();
	private final ListenerManager successListenerManager = new ListenerManager();
	
	private Throwable exception;
	private String returnValue;
	
	public ServiceCaller(ILookupServiceAsync service) {
		this.service = service;
	}

	public void callService(String input) {
		service.lookup(input, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				exception = caught;
				failureListenerManager.notifyListeners();
			}

			@Override
			public void onSuccess(String result) {
				returnValue = result;
				successListenerManager.notifyListeners();
			}
		});
	}
	
	public void addSuccessListener(IListener listener) {
		successListenerManager.addListener(listener);
	}

	public void addFailureListener(IListener listener) {
		failureListenerManager.addListener(listener);
	}

	public Throwable getException() {
		return exception;
	}

	public String getReturnValue() {
		return returnValue;
	}
}
