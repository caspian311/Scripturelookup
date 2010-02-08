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
	private IListener submissionListener;
	private IListener resultsReturnedListener;
	private IListener failureListener;
	private IListener noResultsReturnedListener;

	@Before
	public void setUp() {
		view = mock(ILookupView.class);
		model = mock(ILookupModel.class);

		new LookupPresenter(view, model);

		ArgumentCaptor<IListener> submissionListenerArgument = ArgumentCaptor
				.forClass(IListener.class);
		verify(view).addSubmissionListener(submissionListenerArgument.capture());
		submissionListener = submissionListenerArgument.getValue();

		ArgumentCaptor<IListener> resultsReturnedListenerArgument = ArgumentCaptor
				.forClass(IListener.class);
		verify(model).addResultsReturnedListener(resultsReturnedListenerArgument.capture());
		resultsReturnedListener = resultsReturnedListenerArgument.getValue();

		ArgumentCaptor<IListener> failureListenerArgument = ArgumentCaptor
				.forClass(IListener.class);
		verify(model).addFailureListener(failureListenerArgument.capture());
		failureListener = failureListenerArgument.getValue();

		ArgumentCaptor<IListener> noResultsReturnedListenerArgument = ArgumentCaptor
				.forClass(IListener.class);
		verify(model).addNoResultsReturnedListener(noResultsReturnedListenerArgument.capture());
		noResultsReturnedListener = noResultsReturnedListenerArgument.getValue();
	}

	@Test
	public void whenSubmitButtonPressedDisableSubmitButtonAndShowBusySignal() {
		submissionListener.handleEvent();

		verify(view).disableSubmitButton();
		verify(view).showBusySignal();
	}

	@Test
	public void whenSubmitButtonPressedPullQueryFromViewAndGiveToModel() {
		String query = UUID.randomUUID().toString();

		when(view.getQueryString()).thenReturn(query);

		submissionListener.handleEvent();

		verify(model).queryServer(query);
	}
	
	@Test
	public void whenSubmitButtonPressedPullQueryTypeFromViewAndGiveToModel() {
		String queryType = UUID.randomUUID().toString();

		when(view.getQueryType()).thenReturn(queryType);

		submissionListener.handleEvent();

		verify(model).setQueryType(queryType);
	}

	@Test
	public void whenModelGetsResultsReEnableSubmitButton() {
		resultsReturnedListener.handleEvent();

		verify(view).enableSubmitButton();
	}

	@Test
	public void whenModelSignalsFailureReEnableSubmitButton() {
		failureListener.handleEvent();

		verify(view).enableSubmitButton();
	}

	@Test
	public void whenModelGetsSearchResultsPullVersesFromModelAndPutIntoView() {
		List<Verse> results = new ArrayList<Verse>();

		when(model.searchResults()).thenReturn(results);

		resultsReturnedListener.handleEvent();

		verify(view).showVerses(results);
	}

	@Test
	public void whenModelSignalsFailurePullErrorMessageFromModelAndPutIntoView() {
		String errorMessage = UUID.randomUUID().toString();

		when(model.getErrorMessage()).thenReturn(errorMessage);

		failureListener.handleEvent();

		verify(view).showErrorMessage(errorMessage);
	}

	@Test
	public void whenModelSignalsNoResultsThenNoResultsMessageDisplayedOnView() {
		noResultsReturnedListener.handleEvent();

		verify(view).showNoResultsMessage();
	}
}
