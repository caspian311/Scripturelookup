package net.todd.bible.scripturelookup.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("unchecked")
public class DataManagementModelTest {
	private IDataManagementServiceAsync dataManagementService;
	private IDataManagementModel dataManagementModel;

	@Before
	public void setUp() {
		dataManagementService = mock(IDataManagementServiceAsync.class);
		dataManagementModel = new DataManagementModel(dataManagementService);
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
	public void modelNotifiesWhenFailureReturnOfService() {
		IListener failureListener = mock(IListener.class);

		dataManagementModel.addFailureListener(failureListener);
		dataManagementModel.reloadData();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(dataManagementService).reload(anyString(), captor.capture());

		captor.getValue().onFailure(new Exception());

		verify(failureListener).handleEvent();
	}

	@Test
	public void errorMessageAvailableWhenFailureReturnOfService() {
		String errorMessage = UUID.randomUUID().toString();

		IListener failureListener = mock(IListener.class);

		dataManagementModel.addFailureListener(failureListener);
		dataManagementModel.reloadData();

		ArgumentCaptor<AsyncCallback> captor = ArgumentCaptor.forClass(AsyncCallback.class);

		verify(dataManagementService).reload(anyString(), captor.capture());

		captor.getValue().onFailure(new Exception(errorMessage));

		assertEquals(errorMessage, dataManagementModel.getErrorMessage());
	}
}