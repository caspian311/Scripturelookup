package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.todd.bible.scripturelookup.server.ReferenceParser.Reference;
import net.todd.bible.scripturelookup.server.data.Verse;

import org.junit.Before;
import org.junit.Test;

public class ReferenceSearchEngineTest {
	private ISearchEngine referenceSearchEngine;
	private String query;
	private IReferenceParser referenceParser;
	private IReferenceLookup referenceDao;

	@Before
	public void setUp() {
		referenceParser = mock(IReferenceParser.class);
		referenceDao = mock(IReferenceLookup.class);
		
		referenceSearchEngine = new ReferenceSearchEngine(referenceParser, referenceDao);
		
		query = UUID.randomUUID().toString();
	}

	@Test
	public void queryGivenToReferenceParser() throws ReferenceParsingException {
		referenceSearchEngine.search(query);
		
		verify(referenceParser).parseReference(query);
	}
	
	@Test
	public void parsedReferenceGivenToReferenceDao() {
		Reference reference = mock(Reference.class);
		doReturn(reference).when(referenceParser).parseReference(anyString());

		referenceSearchEngine.search(query);

		verify(referenceDao).lookupReference(reference);
	}
	
	@Test
	public void returnWhateverTheReferenceLookupReturns() {
		List<Verse> verses = Arrays.asList();
		doReturn(verses).when(referenceDao).lookupReference(any(Reference.class));
		
		List<Verse> results = referenceSearchEngine.search(query);

		assertSame(verses, results);
	}
}
