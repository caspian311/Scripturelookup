package net.todd.bible.scripturelookup.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("unchecked")
public class ServiceCallerTest {
	private ILookupServiceAsync service;
	private IServiceCaller serviceCaller;
	private IListener successListener;
	private IListener failureListener;
	private AsyncCallback callback;
	
	@Before
	public void setUp() {
		service = mock(ILookupServiceAsync.class);
		successListener = mock(IListener.class);
		failureListener = mock(IListener.class);

		serviceCaller = new ServiceCaller(service);
		serviceCaller.addSuccessListener(successListener);
		serviceCaller.addFailureListener(failureListener);
		
		String input = UUID.randomUUID().toString();

		serviceCaller.callService(input);
		
		ArgumentCaptor<AsyncCallback> callbackArgument = ArgumentCaptor
				.forClass(AsyncCallback.class);
		verify(service).lookup(eq(input), callbackArgument.capture());
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

		assertSame(exception, serviceCaller.getException());
	}
	
	@Test
	public void successObjectIsMadeAvailable() {
		String returnValue = UUID.randomUUID().toString();

		callback.onSuccess(returnValue);

		assertEquals(returnValue, serviceCaller.getReturnValue());
	}
}