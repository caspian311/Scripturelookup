package net.todd.bible.scripturelookup.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("unchecked")
public class LookupModelTest {
	private ILookupServiceAsync lookupService;
	private IResultsParser parser;
	private ILookupModel lookupModel;

	@Before
	public void setUp() {
		lookupService = mock(ILookupServiceAsync.class);
		parser = mock(IResultsParser.class);
		
		lookupModel = new LookupModel(lookupService, parser);
	}

	@Test
	public void queryStringPassedToLookupService() {
		String query = UUID.randomUUID().toString();

		lookupModel.queryServer(query);

		verify(lookupService).lookup(eq(query), (AsyncCallback<String>) anyObject());
	}

	@Test
	public void failureListenersAreNotifiedWhenFailureOccurs() {
		IListener failureListener = mock(IListener.class);

		lookupModel.addFailureListener(failureListener);


		lookupModel.queryServer(UUID.randomUUID().toString());

		ArgumentCaptor<AsyncCallback> callbackArgument = ArgumentCaptor
				.forClass(AsyncCallback.class);
		verify(lookupService).lookup(anyString(), callbackArgument.capture());
		callbackArgument.getValue().onFailure(new Exception());

		verify(failureListener).handleEvent();
	}

	@Test
	public void errorMessageSetWhenFailureOccurs() {
		String errorMessage = UUID.randomUUID().toString();

		lookupModel.queryServer(UUID.randomUUID().toString());

		ArgumentCaptor<AsyncCallback> callbackArgument = ArgumentCaptor
				.forClass(AsyncCallback.class);
		verify(lookupService).lookup(anyString(), callbackArgument.capture());
		callbackArgument.getValue().onFailure(new Exception(errorMessage));

		assertEquals(errorMessage, lookupModel.getErrorMessage());
	}

	@Test
	public void searchResultListenersAreNotifiedWhenCallReturns() {
		String response = UUID.randomUUID().toString();

		Verse verse1 = mock(Verse.class);
		List<Verse> searchResults = new ArrayList<Verse>();
		searchResults.add(verse1);
		
		IListener searchResultListener = mock(IListener.class);

		lookupModel.addResultsReturnedListener(searchResultListener);

		when(parser.parse(response)).thenReturn(searchResults);

		lookupModel.queryServer(UUID.randomUUID().toString());

		ArgumentCaptor<AsyncCallback> callbackArgument = ArgumentCaptor
				.forClass(AsyncCallback.class);
		verify(lookupService).lookup(anyString(), callbackArgument.capture());
		callbackArgument.getValue().onSuccess(response);

		verify(searchResultListener).handleEvent();
	}

	@Test
	public void searchResultAreAvailableWhenCallReturns() {
		String response = UUID.randomUUID().toString();

		Verse verse1 = mock(Verse.class);
		List<Verse> searchResults = new ArrayList<Verse>();
		searchResults.add(verse1);


		when(parser.parse(response)).thenReturn(searchResults);

		lookupModel.queryServer(UUID.randomUUID().toString());

		ArgumentCaptor<AsyncCallback> callbackArgument = ArgumentCaptor.forClass(AsyncCallback.class);
		verify(lookupService).lookup(anyString(), callbackArgument.capture());
		callbackArgument.getValue().onSuccess(response);

		assertEquals(1, lookupModel.searchResults().size());
		assertSame(verse1, lookupModel.searchResults().get(0));
	}

	@Test
	public void noResultsListenerNotifiedWhenNoResultsAreReturned() {
		IListener listener = mock(IListener.class);

		lookupModel.queryServer(UUID.randomUUID().toString());
		lookupModel.addNoResultsReturnedListener(listener);


		ArgumentCaptor<AsyncCallback> callbackArgument = ArgumentCaptor
				.forClass(AsyncCallback.class);
		verify(lookupService).lookup(anyString(), callbackArgument.capture());
		callbackArgument.getValue().onSuccess("[]");

		verify(listener, times(1)).handleEvent();
	}
}
