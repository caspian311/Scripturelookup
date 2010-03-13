package net.todd.bible.scripturelookup.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.todd.bible.scripturelookup.client.IListener;
import net.todd.bible.scripturelookup.client.SearchResultsMetaData;
import net.todd.bible.scripturelookup.client.Verse;
import net.todd.bible.scripturelookup.client.model.LookupModel;
import net.todd.bible.scripturelookup.client.parsers.IResultsParser;
import net.todd.bible.scripturelookup.client.parsers.ParsedResults;
import net.todd.bible.scripturelookup.client.service.ILookupServiceCaller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class LookupModelTest {
	private ILookupServiceCaller serviceCaller;
	private IResultsParser resultsParser;
	private IListener failureListener;
	private LookupModel lookupModel;
	private IListener successListener;
	private IListener noResultsReturnedListener;
	private IListener resultsReturnedListener;
	private IListener modelFailureListener;

	@Before
	public void setUp() {
		serviceCaller = mock(ILookupServiceCaller.class);
		resultsParser = mock(IResultsParser.class);

		lookupModel = new LookupModel(serviceCaller, resultsParser);

		ArgumentCaptor<IListener> failureListenerArgument = ArgumentCaptor
				.forClass(IListener.class);
		verify(serviceCaller).addFailureListener(failureListenerArgument.capture());
		failureListener = failureListenerArgument.getValue();

		ArgumentCaptor<IListener> successListenerArgument = ArgumentCaptor
				.forClass(IListener.class);
		verify(serviceCaller).addSuccessListener(successListenerArgument.capture());
		successListener = successListenerArgument.getValue();
		
		resultsReturnedListener = mock(IListener.class);
		noResultsReturnedListener = mock(IListener.class);
		modelFailureListener = mock(IListener.class);
		
		lookupModel.addResultsReturnedListener(resultsReturnedListener);
		lookupModel.addNoResultsReturnedListener(noResultsReturnedListener);
		lookupModel.addFailureListener(modelFailureListener);
	}

	@Test
	public void onSuccessfulServiceCallReturnValueIsGivenToParser() {
		String returnValue = UUID.randomUUID().toString();
		doReturn(returnValue).when(serviceCaller).getReturnValue();

		successListener.handleEvent();

		verify(resultsParser).parse(returnValue);
	}

	@Test
	public void whenNoResultsReturnedNoResultsReturnedListenerNotified() {
		ParsedResults parsedResults = mock(ParsedResults.class);
		doReturn(Arrays.asList()).when(parsedResults).getReferenceList();
		doReturn(parsedResults).when(resultsParser).parse(anyString());

		successListener.handleEvent();

		verify(noResultsReturnedListener).handleEvent();
	}

	@Test
	public void whenSomeResultsAreReturnedResultsReturnedListenerNotified() {
		Verse verse1 = mock(Verse.class);
		ParsedResults parsedResults = mock(ParsedResults.class);
		doReturn(Arrays.asList(verse1)).when(parsedResults).getReferenceList();
		doReturn(parsedResults).when(resultsParser).parse(anyString());
		
		successListener.handleEvent();

		verify(resultsReturnedListener).handleEvent();
	}
	
	@Test
	public void whenServiceCallFailsFailureListenersNotified() {
		failureListener.handleEvent();

		verify(modelFailureListener).handleEvent();
	}
	
	@Test
	public void serviceCall() {
		String queryType = UUID.randomUUID().toString();
		String query = UUID.randomUUID().toString();

		lookupModel.setQueryType(queryType);
		lookupModel.queryServer(query);
		
		verify(serviceCaller).callService(queryType, query);
	}
	
	@Test
	public void errorMessageIsPulledFromServiceCall() {
		String errorMessage = UUID.randomUUID().toString();
		doReturn(new Exception(errorMessage)).when(serviceCaller).getException();

		assertEquals(errorMessage, lookupModel.getErrorMessage());
	}

	@Test
	public void searchResultsArePulledFromResultsParser() {
		List<Verse> referenceList = new ArrayList<Verse>();
		ParsedResults parsedResults = mock(ParsedResults.class);
		doReturn(referenceList).when(parsedResults).getReferenceList();
		doReturn(parsedResults).when(resultsParser).parse(anyString());

		successListener.handleEvent();

		assertSame(referenceList, lookupModel.searchResults());
	}
	
	@Test
	public void metaDataIsPulledFromResultsParser() {
		SearchResultsMetaData metaData = mock(SearchResultsMetaData.class);
		ParsedResults parsedResults = mock(ParsedResults.class);
		doReturn(metaData).when(parsedResults).getMetaData();
		doReturn(parsedResults).when(resultsParser).parse(anyString());

		successListener.handleEvent();

		assertSame(metaData, lookupModel.getSearchResultsMetaData());
	}
}
