package net.todd.bible.scripturelookup.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
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

	@Before
	public void setUp() {
		lookupService = mock(ILookupServiceAsync.class);
		parser = mock(IResultsParser.class);
	}

	@Test
	public void queryStringPassedToLookupService() {
		String query = UUID.randomUUID().toString();

		ILookupModel lookupModel = new LookupModel(lookupService, parser);

		lookupModel.queryServer(query);

		verify(lookupService).lookup(eq(query), (AsyncCallback<String>) anyObject());
	}

	@Test
	public void failureListenersAreNotifiedWhenFailureOccurs() {
		IListener failureListener = mock(IListener.class);

		ILookupModel lookupModel = new LookupModel(lookupService, parser);
		lookupModel.addFailureListener(failureListener);

		ArgumentCaptor<AsyncCallback> argument = ArgumentCaptor.forClass(AsyncCallback.class);

		lookupModel.queryServer(UUID.randomUUID().toString());

		verify(lookupService).lookup(anyString(), argument.capture());
		AsyncCallback<String> callback = argument.getValue();
		callback.onFailure(new Exception());

		verify(failureListener).handleEvent();
	}

	@Test
	public void errorMessageSetWhenFailureOccurs() {
		String errorMessage = UUID.randomUUID().toString();

		ILookupModel lookupModel = new LookupModel(lookupService, parser);

		ArgumentCaptor<AsyncCallback> argument = ArgumentCaptor.forClass(AsyncCallback.class);

		lookupModel.queryServer(UUID.randomUUID().toString());

		verify(lookupService).lookup(anyString(), argument.capture());
		AsyncCallback<String> callback = argument.getValue();
		callback.onFailure(new Exception(errorMessage));

		assertEquals(errorMessage, lookupModel.getErrorMessage());
	}

	@Test
	public void searchResultListenersAreNotifiedWhenCallReturns() {
		IListener searchResultListener = mock(IListener.class);

		ILookupModel lookupModel = new LookupModel(lookupService, parser);
		lookupModel.addResultsReturnedListener(searchResultListener);

		ArgumentCaptor<AsyncCallback> argument = ArgumentCaptor.forClass(AsyncCallback.class);

		lookupModel.queryServer(UUID.randomUUID().toString());

		verify(lookupService).lookup(anyString(), argument.capture());
		AsyncCallback<String> callback = argument.getValue();
		callback.onSuccess("");

		verify(searchResultListener).handleEvent();
	}

	@Test
	public void searchResultAreEmptyWhenNoResultsAreReturned() {
		ILookupModel lookupModel = new LookupModel(lookupService, parser);

		ArgumentCaptor<AsyncCallback> argument = ArgumentCaptor.forClass(AsyncCallback.class);

		lookupModel.queryServer(UUID.randomUUID().toString());

		verify(lookupService).lookup(anyString(), argument.capture());
		AsyncCallback<String> callback = argument.getValue();
		callback.onSuccess("");

		assertEquals(0, lookupModel.searchResults().size());
	}

	@Test
	public void searchResultAreAvailableWhenCallReturns() {
		String response = UUID.randomUUID().toString();
		
		Verse verse1 = mock(Verse.class);
		List<Verse> searchResults = new ArrayList<Verse>();
		searchResults.add(verse1);

		ILookupModel lookupModel = new LookupModel(lookupService, parser);

		ArgumentCaptor<AsyncCallback> argument = ArgumentCaptor.forClass(AsyncCallback.class);

		when(parser.parse(response)).thenReturn(searchResults);

		lookupModel.queryServer(UUID.randomUUID().toString());

		verify(lookupService).lookup(anyString(), argument.capture());
		AsyncCallback<String> callback = argument.getValue();
		callback.onSuccess(response);

		assertEquals(1, lookupModel.searchResults().size());
		assertSame(verse1, lookupModel.searchResults().get(0));
	}
}
