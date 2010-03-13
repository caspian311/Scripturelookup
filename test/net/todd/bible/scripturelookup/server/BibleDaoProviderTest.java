package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import net.todd.bible.scripturelookup.server.data.BibleDaoProvider;
import net.todd.bible.scripturelookup.server.data.IBibleDao;

import org.junit.Test;

public class BibleDaoProviderTest {
	@Test
	public void singletonNatureOfReturnedDao() {
		assertNotNull(BibleDaoProvider.getBibleDao());
		assertTrue(BibleDaoProvider.getBibleDao() instanceof IBibleDao);
		assertSame(BibleDaoProvider.getBibleDao(), BibleDaoProvider.getBibleDao());
	}
}
