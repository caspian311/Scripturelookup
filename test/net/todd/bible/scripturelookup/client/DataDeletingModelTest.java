package net.todd.bible.scripturelookup.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class DataDeletingModelTest {
	private IDataDeletingServiceCaller dataDeletingServiceCaller;
	private IListener deletionSuccessfullListener;
	private IListener deletionFailedListener;
	private IDataDeletingModel dataDeletingModel;

	@Before
	public void setUp() {
		dataDeletingServiceCaller = mock(IDataDeletingServiceCaller.class);

		dataDeletingModel = new DataDeletingModel(dataDeletingServiceCaller);
		
		ArgumentCaptor<IListener> deletionSuccessListenerCapture = ArgumentCaptor
				.forClass(IListener.class);
		verify(dataDeletingServiceCaller).addSuccessListener(
				deletionSuccessListenerCapture.capture());
		deletionSuccessfullListener = deletionSuccessListenerCapture.getValue();

		ArgumentCaptor<IListener> deletionFailedListenerCapture = ArgumentCaptor
				.forClass(IListener.class);
		verify(dataDeletingServiceCaller).addFailureListener(
				deletionFailedListenerCapture.capture());
		deletionFailedListener = deletionFailedListenerCapture.getValue();
	}

	@Test
	public void callDeleteDataServiceWhenDeletingData() {
		dataDeletingModel.deleteData();

		verify(dataDeletingServiceCaller).callService();
	}

	@Test
	public void errorMessageIsMadeAvailableWhenDataDeletionFails() {
		String errorMessage = UUID.randomUUID().toString();
		doReturn(new Exception(errorMessage)).when(dataDeletingServiceCaller).getException();

		deletionFailedListener.handleEvent();

		assertEquals(errorMessage, dataDeletingModel.getErrorMessage());
	}

	@Test
	public void notifyDeleteFailListenersWhenDataDeletionFails() {
		IListener listener = mock(IListener.class);
		dataDeletingModel.addFailureListener(listener);

		deletionFailedListener.handleEvent();

		verify(listener).handleEvent();
	}

	@Test
	public void notifyDeleteSuccessfullListenersWhenDataDeletionSucceeds() {
		IListener listener = mock(IListener.class);
		dataDeletingModel.addDataDeletionListener(listener);

		deletionSuccessfullListener.handleEvent();

		verify(listener).handleEvent();
	}
}
