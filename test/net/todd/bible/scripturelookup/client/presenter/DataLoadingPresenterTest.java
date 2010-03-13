package net.todd.bible.scripturelookup.client.presenter;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;
import java.util.UUID;

import net.todd.bible.scripturelookup.client.IListener;
import net.todd.bible.scripturelookup.client.model.IDataLoadingModel;
import net.todd.bible.scripturelookup.client.presenter.DataLoadingPresenter;
import net.todd.bible.scripturelookup.client.view.IDataManagementView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

public class DataLoadingPresenterTest {
	private IDataManagementView view;
	private IDataLoadingModel model;
	private IListener reloadButtonListener;
	private IListener failureListener;
	private IListener dataReloadedListener;
	private IListener progressListener;

	@Before
	public void setUp() {
		view = mock(IDataManagementView.class);
		model = mock(IDataLoadingModel.class);

		new DataLoadingPresenter(view, model);
		
		ArgumentCaptor<IListener> reloadButtonListenerCaptor = ArgumentCaptor
				.forClass(IListener.class);
		verify(view).addReloadButtonListener(reloadButtonListenerCaptor.capture());
		reloadButtonListener = reloadButtonListenerCaptor.getValue();

		ArgumentCaptor<IListener> failureListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addFailureListener(failureListenerCaptor.capture());
		failureListener = failureListenerCaptor.getValue();
		
		ArgumentCaptor<IListener> dataReloadedCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addDataReloadedListener(dataReloadedCaptor.capture());
		dataReloadedListener = dataReloadedCaptor.getValue();
		
		ArgumentCaptor<IListener> progressListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addProgressListener(progressListenerCaptor.capture());
		progressListener = progressListenerCaptor.getValue();
	}
	
	@Test
	public void showBusySignalThenTellModelToReloadDataWhenReloadButtonPressed() {
		reloadButtonListener.handleEvent();

		InOrder inOrder = inOrder(view, model);
		inOrder.verify(view).showReloadingBusySignal();
		inOrder.verify(model).reloadData();
	}
	
	@Test
	public void whenModelErrorOccursViewShowErrorMessage() {
		String errorMessage = UUID.randomUUID().toString();
		when(model.getErrorMessage()).thenReturn(errorMessage);
		
		failureListener.handleEvent();

		verify(view).showErrorMessage(errorMessage);
	}

	@Test
	public void whenReloadSuccessfulShowReloadSuccessMessage() {
		dataReloadedListener.handleEvent();

		verify(view).showReloadSuccessMessage();
	}
	
	@Test
	public void whenProgressIsMadeShowPullPercentCompleteFromModelAndGiveToView() {
		double percentComplete = new Random().nextDouble();
		doReturn(percentComplete).when(model).getPercentComplete();
		
		progressListener.handleEvent();

		verify(view).updatePercentComplete(percentComplete);
	}
}