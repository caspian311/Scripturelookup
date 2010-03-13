package net.todd.bible.scripturelookup.client.service;

import net.todd.bible.scripturelookup.client.IListener;
import net.todd.bible.scripturelookup.client.ListenerManager;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class ServiceCaller implements IServiceCaller {
	private final ListenerManager failureListenerManager = new ListenerManager();
	private final ListenerManager successListenerManager = new ListenerManager();

	private Throwable exception;
	private String returnValue;
	
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
	
	private void setFailureException(Throwable caught) {
		this.exception = caught;
	}
	
	private void notifyFailureListeners() {
		failureListenerManager.notifyListeners();
	}
	

	private void setReturnValue(String result) {
		returnValue = result;
	}
	
	private void notifySuccessListeners() {
		successListenerManager.notifyListeners();
	}
	
	protected AsyncCallback<String> getCallback() {
		return new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				setFailureException(caught);
				notifyFailureListeners();
			}

			@Override
			public void onSuccess(String result) {
				setReturnValue(result);
				notifySuccessListeners();
			}
		};
	}
}
