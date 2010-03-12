package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DataLoaderProviderTest {
	@Test
	public void singletonNature() {
		assertTrue(DataLoaderProvider.getDataLoader() instanceof DataLoader);
		assertSame(DataLoaderProvider.getDataLoader(), DataLoaderProvider.getDataLoader());
	}
}
