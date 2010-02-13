package net.todd.bible.scripturelookup.client;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class FileProviderTest {
	private IFileProvider fileProvider;

	@Before
	public void setUp() {
		fileProvider = new FileProvider();
	}

	@Test
	public void filesToLoadAreNotNull() {
		assertNotNull(fileProvider.filesToLoad());
	}
}
