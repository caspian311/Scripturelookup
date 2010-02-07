package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DataLoaderProviderTest {
	@Test
	public void returnsADataLoader() {
		assertTrue(DataLoaderProvider.getDataLoader() instanceof DataLoader);
	}

	@Test
	public void singletonNature() {
		assertSame(DataLoaderProvider.getDataLoader(), DataLoaderProvider.getDataLoader());
	}
}
