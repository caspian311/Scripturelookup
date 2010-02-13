package net.todd.bible.scripturelookup.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

@SuppressWarnings("unchecked")
public class DataLoadingModelTest {
	private IDataDeletingServiceCaller dataDeletingServiceCaller;
	private IDataLoadingServiceCaller dataLoadingServiceCaller;
	private IDataLoadingModel dataLoadingModel;
	private IListener deletionSuccessfullListener;
	private IListener deletionFailedListener;
	private IListener loadingSuccessfullListener;
	private IListener loadingFailedListener;

	@Before
	public void setUpMocks() {
		dataDeletingServiceCaller = mock(IDataDeletingServiceCaller.class);
		dataLoadingServiceCaller = mock(IDataLoadingServiceCaller.class);
		
		dataLoadingModel = new DataLoadingModel(dataDeletingServiceCaller, dataLoadingServiceCaller);
		
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

		ArgumentCaptor<IListener> loadingSuccessListenerCapture = ArgumentCaptor
				.forClass(IListener.class);
		verify(dataLoadingServiceCaller)
				.addSuccessListener(loadingSuccessListenerCapture.capture());
		loadingSuccessfullListener = loadingSuccessListenerCapture.getValue();

		ArgumentCaptor<IListener> loadingFailedListenerCapture = ArgumentCaptor
				.forClass(IListener.class);
		verify(dataLoadingServiceCaller).addFailureListener(loadingFailedListenerCapture.capture());
		loadingFailedListener = loadingFailedListenerCapture.getValue();
	}

	@Test
	public void callDeleteDataServiceWhenDeletingData() {
		dataLoadingModel.deleteData();

		verify(dataDeletingServiceCaller).callService();
	}

	@Test
	public void errorMessageIsMadeAvailableWhenDataDeletionFails() {
		String errorMessage = UUID.randomUUID().toString();
		doReturn(new Exception(errorMessage)).when(dataDeletingServiceCaller).getException();

		deletionFailedListener.handleEvent();

		assertEquals(errorMessage, dataLoadingModel.getErrorMessage());
	}
	
	@Test
	public void notifyDeleteFailListenersWhenDataDeletionFails() {
		IListener listener = mock(IListener.class);
		dataLoadingModel.addFailureListener(listener);
		
		deletionFailedListener.handleEvent();

		verify(listener).handleEvent();
	}
	
	@Test
	public void notifyDeleteSuccessfullListenersWhenDataDeletionSucceeds() {
		IListener listener = mock(IListener.class);
		dataLoadingModel.addDataDeletionListener(listener);

		deletionSuccessfullListener.handleEvent();

		verify(listener).handleEvent();
	}
}