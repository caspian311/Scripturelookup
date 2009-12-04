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
public class DataManagementModelTest {
	private IDataLoadingServiceAsync dataManagementService;
	private IIndexServiceAsync indexService;
	private IDataLoadingModel dataManagementModel;

	@Before
	public void setUp() {
		dataManagementService = mock(IDataLoadingServiceAsync.class);
		indexService = mock(IIndexServiceAsync.class);
		dataManagementModel = new DataLoadingModel(dataManagementService, indexService);
	}
	
	@Test
	public void callDataManagementServicewhenReloading() {
		dataManagementModel.reloadData();
		
		verify(dataManagementService).reload(anyString(), (AsyncCallback) anyObject());
	}
	
	@Test
	public void modelNotifiesWhenSuccessfulReturnOfService() {
		IListener successListener = mock(IListener.class);

		dataManagementModel.addSuccessListener(successListener);
		dataManagementModel.reloadData();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(dataManagementService).reload(anyString(), captor.capture());
		
		captor.getValue().onSuccess("");
		
		verify(successListener).handleEvent();
	}
	
	@Test
	public void errorMessageIsMadeAvailableThenModelNotifiesWhenFailureReturnOfService() {
		IListener failureListener = mock(IListener.class);
		Exception exception = mock(Exception.class);

		dataManagementModel.addFailureListener(failureListener);
		dataManagementModel.reloadData();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(dataManagementService).reload(anyString(), captor.capture());

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
		
		dataManagementModel.addFailureListener(failureListener);
		dataManagementModel.reloadData();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(dataManagementService).reload(anyString(), captor.capture());

		captor.getValue().onFailure(exception);

		assertEquals(errorMessage, dataManagementModel.getErrorMessage());
	}
}