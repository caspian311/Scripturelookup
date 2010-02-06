package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class LookupServiceTest {
	private IBibleSearchFactory bibleSearchFactory;
	private ISearchEngine searchEngine;
	private LookupService lookupService;
	private IJSONWriter jsonWriter;
	private String queryType;
	private String query;
	private List<Verse> searchResults;
	private String jsonWriterOutput;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		bibleSearchFactory = mock(IBibleSearchFactory.class);
		searchEngine = mock(ISearchEngine.class);
		jsonWriter = mock(IJSONWriter.class);
		lookupService = new LookupService(jsonWriter, bibleSearchFactory);

		jsonWriterOutput = UUID.randomUUID().toString();
		doReturn(searchEngine).when(bibleSearchFactory).getSearchEngine(anyString());

		searchResults = new ArrayList<Verse>();
		doReturn(searchResults).when(searchEngine).search(anyString());
		doReturn(jsonWriterOutput).when(jsonWriter).writeOut(any(List.class));
		
		queryType = UUID.randomUUID().toString();
		query = UUID.randomUUID().toString();
	}

	@Test
	public void queryTypeIsGivenToTheFactory() throws Exception {
		lookupService.lookup(queryType, query);
		
		verify(bibleSearchFactory).getSearchEngine(queryType);
	}
	
	@Test
	public void queryIsGivenToSearchEngine() throws Exception {
		lookupService.lookup(queryType, query);
		
		verify(searchEngine).search(query);
	}

	@Test
	public void searchResutlsAreGivenToJSONWriter() throws Exception {
		lookupService.lookup(queryType, query);
		
		verify(jsonWriter).writeOut(searchResults);
	}
	
	@Test
	public void returnWhatTheJSONWriterReturns() throws Exception {
		String actualResults = lookupService.lookup(queryType, query);

		assertEquals(jsonWriterOutput, actualResults);
	}
}
