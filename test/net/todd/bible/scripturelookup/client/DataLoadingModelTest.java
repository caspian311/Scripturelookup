package net.todd.bible.scripturelookup.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
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
	private IDataDeletingServiceAsync dataDeletingService;
	private IDataLoadingServiceAsync dataLoadingService;
	private IIndexServiceAsync indexService;
	private IDataLoadingModel dataLoadingModel;

	@Before
	public void setUp() {
		dataDeletingService = mock(IDataDeletingServiceAsync.class);
		dataLoadingService = mock(IDataLoadingServiceAsync.class);
		indexService = mock(IIndexServiceAsync.class);
		dataLoadingModel = new DataLoadingModel(dataDeletingService, dataLoadingService,
				indexService);
	}
	
	@Test
	public void callDataManagementServicewhenReloading() {
		dataLoadingModel.reloadData();
		
		verify(dataLoadingService).loadAllData((AsyncCallback) anyObject());
	}
	
	@Test
	public void reloadListenersNotifiesWhenDataGetsReloaded() {
		IListener successListener = mock(IListener.class);

		dataLoadingModel.addDataReloadedListener(successListener);
		dataLoadingModel.reloadData();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(dataLoadingService).loadAllData(captor.capture());
		
		captor.getValue().onSuccess("");
		
		verify(successListener).handleEvent();
	}
	
	@Test
	public void deletionListenersAreNotifiedWhenSuccessfulDeletionOccurrs() {
		IListener successListener = mock(IListener.class);

		dataLoadingModel.addDataDeletionListener(successListener);
		dataLoadingModel.deleteData();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(dataDeletingService).deleteAllData(captor.capture());

		captor.getValue().onSuccess("");

		verify(successListener).handleEvent();
	}
	
	@Test
	public void createIndexListenersAreNotifiedWhenSuccessfulCreationOfIndexOccurrs() {
		IListener successListener = mock(IListener.class);

		dataLoadingModel.addIndexCreatedListener(successListener);
		dataLoadingModel.createIndex();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(indexService).createIndex(captor.capture());

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

		verify(dataLoadingService).loadAllData(captor.capture());

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

		verify(dataLoadingService).loadAllData(captor.capture());

		captor.getValue().onFailure(exception);

		assertEquals(errorMessage, dataLoadingModel.getErrorMessage());
	}
}