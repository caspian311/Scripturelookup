package net.todd.bible.scripturelookup.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class LookupPresenterTest {
	private ILookupView view;
	private ILookupModel model;

	@Before
	public void setUp() {
		view = mock(ILookupView.class);
		model = mock(ILookupModel.class);
	}
	
	@Test
	public void whenSubmitButtonPressedShowBusySignal() {
		ArgumentCaptor<IListener> argument = ArgumentCaptor.forClass(IListener.class);

		new LookupPresenter(view, model);
		
		verify(view).addSubmissionListener(argument.capture());
		
		argument.getValue().handleEvent();
		
		verify(view).disableSubmitButton();
		verify(view).showBusySignal();
	}
	
	@Test
	public void whenSubmitButtonPressedDisableSubmitButton() {
		ArgumentCaptor<IListener> argument = ArgumentCaptor.forClass(IListener.class);

		new LookupPresenter(view, model);

		verify(view).addSubmissionListener(argument.capture());

		argument.getValue().handleEvent();

		verify(view).disableSubmitButton();
	}
	
	@Test
	public void whenSubmitButtonPressedPullQueryFromViewAndGiveToModel() {
		String query = UUID.randomUUID().toString();
		
		ArgumentCaptor<IListener> argument = ArgumentCaptor.forClass(IListener.class);

		new LookupPresenter(view, model);

		verify(view).addSubmissionListener(argument.capture());
		when(view.getQueryString()).thenReturn(query);

		argument.getValue().handleEvent();

		verify(model).queryServer(query);
	}
	
	@Test
	public void whenModelGetsResultsReEnableSubmitButton() {
		ArgumentCaptor<IListener> argument = ArgumentCaptor.forClass(IListener.class);

		new LookupPresenter(view, model);

		verify(model).addResultsReturnedListener(argument.capture());

		argument.getValue().handleEvent();

		verify(view).enableSubmitButton();
	}
	
	@Test
	public void whenModelSignalsFailureReEnableSubmitButton() {
		ArgumentCaptor<IListener> argument = ArgumentCaptor.forClass(IListener.class);

		new LookupPresenter(view, model);

		verify(model).addFailureListener(argument.capture());

		argument.getValue().handleEvent();

		verify(view).enableSubmitButton();
	}

	@Test
	public void whenModelGetsSearchResultsPullVersesFromModelAndPutIntoView() {
		List<Verse> results = new ArrayList<Verse>();
		
		ArgumentCaptor<IListener> argument = ArgumentCaptor.forClass(IListener.class);

		new LookupPresenter(view, model);

		verify(model).addResultsReturnedListener(argument.capture());

		when(model.searchResults()).thenReturn(results);
		
		argument.getValue().handleEvent();

		verify(view).showVerses(results);
	}
	
	@Test
	public void whenModelSignalsFailurePullErrorMessageFromModelAndPutIntoView() {
		String errorMessage = UUID.randomUUID().toString();

		ArgumentCaptor<IListener> argument = ArgumentCaptor.forClass(IListener.class);

		new LookupPresenter(view, model);

		verify(model).addFailureListener(argument.capture());

		when(model.getErrorMessage()).thenReturn(errorMessage);

		argument.getValue().handleEvent();

		verify(view).showErrorMessage(errorMessage);
	}
}
