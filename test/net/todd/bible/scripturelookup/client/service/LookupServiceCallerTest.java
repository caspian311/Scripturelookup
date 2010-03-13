package net.todd.bible.scripturelookup.client.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import net.todd.bible.scripturelookup.client.IListener;
import net.todd.bible.scripturelookup.client.service.ILookupServiceAsync;
import net.todd.bible.scripturelookup.client.service.ILookupServiceCaller;
import net.todd.bible.scripturelookup.client.service.LookupServiceCaller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("unchecked")
public class LookupServiceCallerTest {
	private ILookupServiceAsync service;
	private ILookupServiceCaller lookupServiceCaller;
	private IListener successListener;
	private IListener failureListener;
	private AsyncCallback callback;
	
	@Before
	public void setUp() {
		service = mock(ILookupServiceAsync.class);
		successListener = mock(IListener.class);
		failureListener = mock(IListener.class);

		lookupServiceCaller = new LookupServiceCaller(service);
		lookupServiceCaller.addSuccessListener(successListener);
		lookupServiceCaller.addFailureListener(failureListener);
		
		String query = UUID.randomUUID().toString();
		String queryType = UUID.randomUUID().toString();

		lookupServiceCaller.callService(queryType, query);
		
		ArgumentCaptor<AsyncCallback> callbackArgument = ArgumentCaptor
				.forClass(AsyncCallback.class);
		verify(service).lookup(eq(queryType), eq(query), callbackArgument.capture());
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

		assertSame(exception, lookupServiceCaller.getException());
	}
	
	@Test
	public void successObjectIsMadeAvailable() {
		String returnValue = UUID.randomUUID().toString();

		callback.onSuccess(returnValue);

		assertEquals(returnValue, lookupServiceCaller.getReturnValue());
	}
}
