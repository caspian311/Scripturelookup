package net.todd.bible.scripturelookup.client.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import net.todd.bible.scripturelookup.client.IListener;
import net.todd.bible.scripturelookup.client.service.DataLoadingServiceCaller;
import net.todd.bible.scripturelookup.client.service.IDataLoadingServiceAsync;
import net.todd.bible.scripturelookup.client.service.IDataLoadingServiceCaller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("unchecked")
public class DataLoadingServiceCallerTest {
	private IDataLoadingServiceAsync service;
	private IDataLoadingServiceCaller dataLoadingServiceCaller;
	private IListener successListener;
	private IListener failureListener;
	private AsyncCallback callback;

	@Before
	public void setUp() {
		service = mock(IDataLoadingServiceAsync.class);
		successListener = mock(IListener.class);
		failureListener = mock(IListener.class);

		dataLoadingServiceCaller = new DataLoadingServiceCaller(service);
		dataLoadingServiceCaller.addSuccessListener(successListener);
		dataLoadingServiceCaller.addFailureListener(failureListener);

		String part = UUID.randomUUID().toString();

		dataLoadingServiceCaller.callService(part);

		ArgumentCaptor<AsyncCallback> callbackArgument = ArgumentCaptor
				.forClass(AsyncCallback.class);
		verify(service).loadAllData(eq(part), callbackArgument.capture());
		callback = callbackArgument.getValue();
	}

	@Test
	public void notifyFailureListenersWhenServiceCallFails() {
		callback.onFailure(null);

		verify(failureListener).handleEvent();
	}

	@Test
	public void notifySuccessListenersWhenServiceCallSucceeds() {
		callback.onSuccess(null);

		verify(successListener).handleEvent();
	}

	@Test
	public void failureExceptionIsMadeAvailable() {
		Throwable exception = new Exception();

		callback.onFailure(exception);

		assertSame(exception, dataLoadingServiceCaller.getException());
	}

	@Test
	public void successObjectIsMadeAvailable() {
		String returnValue = UUID.randomUUID().toString();

		callback.onSuccess(returnValue);

		assertEquals(returnValue, dataLoadingServiceCaller.getReturnValue());
	}
}
