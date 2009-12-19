package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SearchEngineProviderTest {
	@Test
	public void singletonNatureOfReturnedDao() {
		assertSame(SearchEngineProvider.getSearchEngine(), SearchEngineProvider.getSearchEngine());
	}

	@Test
	public void neverReturnsNull() {
		assertNotNull(SearchEngineProvider.getSearchEngine());
	}

	@Test
	public void returnsASearchEngine() {
		assertTrue(SearchEngineProvider.getSearchEngine() instanceof ISearchEngine);
	}
}
