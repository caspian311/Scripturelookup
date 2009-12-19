package net.todd.bible.scripturelookup.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("unchecked")
public class DataLoadingModelTest {
	private IDataLoadingServiceAsync dataLoadingService;
	private IIndexServiceAsync indexService;
	private IDataLoadingModel dataLoadingModel;

	@Before
	public void setUp() {
		dataLoadingService = mock(IDataLoadingServiceAsync.class);
		indexService = mock(IIndexServiceAsync.class);
		dataLoadingModel = new DataLoadingModel(dataLoadingService, indexService);
	}
	
	@Test
	public void callDataManagementServicewhenReloading() {
		dataLoadingModel.reloadData();
		
		verify(dataLoadingService).reload(anyString(), (AsyncCallback) anyObject());
	}
	
	@Test
	public void modelNotifiesWhenSuccessfulReturnOfService() {
		IListener successListener = mock(IListener.class);

		dataLoadingModel.addDataReloadedListener(successListener);
		dataLoadingModel.reloadData();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(dataLoadingService).reload(anyString(), captor.capture());
		
		captor.getValue().onSuccess("");
		
		verify(successListener).handleEvent();
	}
	
	@Test
	public void errorMessageIsMadeAvailableThenModelNotifiesWhenFailureReturnOfService() {
		IListener failureListener = mock(IListener.class);
		Exception exception = mock(Exception.class);

		dataLoadingModel.addFailureListener(failureListener);
		dataLoadingModel.reloadData();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(dataLoadingService).reload(anyString(), captor.capture());

		captor.getValue().onFailure(exception);

		InOrder inOrder = inOrder(failureListener, exception);

		inOrder.verify(exception).getMessage();
		inOrder.verify(failureListener).handleEvent();
	}

	@Test
	public void errorMessagePulledFromException() {
		String errorMessage = UUID.randomUUID().toString();
		
		IListener failureListener = mock(IListener.class);
		Exception exception = mock(Exception.class);

		when(exception.getMessage()).thenReturn(errorMessage);
		
		dataLoadingModel.addFailureListener(failureListener);
		dataLoadingModel.reloadData();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(dataLoadingService).reload(anyString(), captor.capture());

		captor.getValue().onFailure(exception);

		assertEquals(errorMessage, dataLoadingModel.getErrorMessage());
	}
	
	@Test
	public void whenModelRebuildsIndexItCallsTheIndexService() {
		dataLoadingModel.rebuildIndex();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(indexService).rebuildIndex(anyString(), captor.capture());
	}

	@Test
	public void modelNotifiesListenersWhenIndexRebuiltSuccessfully() {
		IListener successListener = mock(IListener.class);

		dataLoadingModel.addIndexBuildSuccessListener(successListener);
		dataLoadingModel.rebuildIndex();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(indexService).rebuildIndex(anyString(), captor.capture());

		captor.getValue().onSuccess("");
		
		verify(successListener).handleEvent();
	}
	
	@Test
	public void modelNotifiesFailureListenersWhenIndexRebuildingFails() {
		IListener failureListener = mock(IListener.class);

		dataLoadingModel.addFailureListener(failureListener);
		dataLoadingModel.rebuildIndex();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(indexService).rebuildIndex(anyString(), captor.capture());

		captor.getValue().onFailure(new Exception());

		verify(failureListener).handleEvent();
	}

	@Test
	public void modelProvidesIndexRebuildingErrorWhenFailureOccurs() {
		String errorMessage = UUID.randomUUID().toString();
		
		IListener failureListener = mock(IListener.class);
		Exception exception = mock(Exception.class);

		when(exception.getMessage()).thenReturn(errorMessage);

		dataLoadingModel.addIndexBuildSuccessListener(failureListener);
		dataLoadingModel.rebuildIndex();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(indexService).rebuildIndex(anyString(), captor.capture());

		captor.getValue().onFailure(exception);

		assertEquals(errorMessage, dataLoadingModel.getErrorMessage());
	}
}