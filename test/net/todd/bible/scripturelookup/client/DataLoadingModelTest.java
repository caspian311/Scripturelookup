package net.todd.bible.scripturelookup.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class DataLoadingModelTest {
	private IDataLoadingServiceCaller dataLoadingServiceCaller;
	private IDataLoadingModel dataLoadingModel;
	private IListener loadingSuccessfullListener;
	private IListener loadingFailedListener;
	private IFileProvider fileProvider;

	@Before
	public void setUpMocks() {
		dataLoadingServiceCaller = mock(IDataLoadingServiceCaller.class);
		fileProvider = mock(IFileProvider.class);
		
		dataLoadingModel = new DataLoadingModel(dataLoadingServiceCaller, fileProvider);
		
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
	public void callDataLoadingServiceWhenReloadData() {
		dataLoadingModel.reloadData();

		verify(dataLoadingServiceCaller).callService(anyString());
	}

	@Test
	public void errorMessageIsMadeAvailableWhenDataLoadingFails() {
		String errorMessage = UUID.randomUUID().toString();
		doReturn(new Exception(errorMessage)).when(dataLoadingServiceCaller).getException();

		loadingFailedListener.handleEvent();

		assertEquals(errorMessage, dataLoadingModel.getErrorMessage());
	}

	@Test
	public void notifyDataLoadingFailedListenersWhenDataLoadingFails() {
		IListener listener = mock(IListener.class);
		dataLoadingModel.addFailureListener(listener);

		loadingFailedListener.handleEvent();

		verify(listener).handleEvent();
	}
}