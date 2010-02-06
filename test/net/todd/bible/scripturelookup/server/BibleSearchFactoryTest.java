package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class BibleSearchFactoryTest {
	private BibleSearchFactory bibleSearchFactory;

	@Before
	public void setUp() {
		bibleSearchFactory = new BibleSearchFactory();
	}

	@Test
	public void whenAskedForAKeywordSearchEngineReturnLuceneSearchEngine() {
		assertTrue(bibleSearchFactory.getSearchEngine("keyword") instanceof LuceneSearchEngine);
	}
	
	@Test
	public void whenAskedForAReferenceSearchEngineReturnReferenceSearchEngine() {
		assertTrue(bibleSearchFactory.getSearchEngine("reference") instanceof ReferenceSearchEngine);
	}
	
	@Test(expected = RuntimeException.class)
	public void whenAskedForAnOddSearchEngineThrowRuntimeException() {
		bibleSearchFactory.getSearchEngine(UUID.randomUUID().toString());
	}
}
