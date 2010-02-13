package net.todd.bible.scripturelookup.client;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

public class DataLoadingPresenterTest {
	private IDataLoadingView view;
	private IDataLoadingModel model;

	@Before
	public void setUp() {
		view = mock(IDataLoadingView.class);
		model = mock(IDataLoadingModel.class);

		new DataLoadingPresenter(view, model);
	}
	
	@Test
	public void showBusySignalThenTellModelToDeleteDataWhenButtonDeteDataPressed() {
		ArgumentCaptor<IListener> captor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addDeleteButtonListener(captor.capture());

		captor.getValue().handleEvent();

		InOrder inOrder = inOrder(view, model);

		inOrder.verify(view).showDeletingBusySignal();
		inOrder.verify(model).deleteData();
	}

	@Test
	public void showBusySignalThenTellModelToReloadDataWhenReloadButtonPressed() {
		ArgumentCaptor<IListener> captor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addReloadButtonListener(captor.capture());

		captor.getValue().handleEvent();

		InOrder inOrder = inOrder(view, model);

		inOrder.verify(view).showReloadingBusySignal();
		inOrder.verify(model).reloadData();
	}
	
	@Test
	public void whenModelErrorOccursViewShowErrorMessage() {
		String errorMessage = UUID.randomUUID().toString();
		
		ArgumentCaptor<IListener> captor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addFailureListener(captor.capture());

		when(model.getErrorMessage()).thenReturn(errorMessage);
		
		captor.getValue().handleEvent();

		verify(view).showErrorMessage(errorMessage);
	}

	@Test
	public void whenDeletionSuccessfulShowDeltionSuccessMessage() {
		ArgumentCaptor<IListener> captor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addDataDeletionListener(captor.capture());

		captor.getValue().handleEvent();

		verify(view).showDeletionSuccessMessage();
	}

	@Test
	public void whenReloadSuccessfulShowReloadSuccessMessage() {
		ArgumentCaptor<IListener> captor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addDataReloadedListener(captor.capture());

		captor.getValue().handleEvent();

		verify(view).showReloadSuccessMessage();
	}
}