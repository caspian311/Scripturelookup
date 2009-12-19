package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BibleDaoProviderTest {
	@Test
	public void singletonNatureOfReturnedDao() {
		assertSame(BibleDaoProvider.getBibleDao(), BibleDaoProvider.getBibleDao());
	}
	
	@Test
	public void neverReturnsNull() {
		assertNotNull(BibleDaoProvider.getBibleDao());
	}
	
	@Test
	public void returnsABibleDao() {
		assertTrue(BibleDaoProvider.getBibleDao() instanceof IBibleDao);
	}
}
