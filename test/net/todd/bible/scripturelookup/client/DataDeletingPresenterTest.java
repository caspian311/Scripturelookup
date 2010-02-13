package net.todd.bible.scripturelookup.client;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

public class DataDeletingPresenterTest {
	private IDataManagementView view;
	private IDataDeletingModel model;

	@Before
	public void setUp() {
		view = mock(IDataManagementView.class);
		model = mock(IDataDeletingModel.class);

		new DataDeletingPresenter(view, model);
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
	public void whenDeletionSuccessfulShowDeltionSuccessMessage() {
		ArgumentCaptor<IListener> captor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addDataDeletionListener(captor.capture());

		captor.getValue().handleEvent();

		verify(view).showDeletionSuccessMessage();
	}
}
