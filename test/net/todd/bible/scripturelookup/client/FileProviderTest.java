package net.todd.bible.scripturelookup.client;

import static org.junit.Assert.assertEquals;
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
	
	@Test
	public void thereShouldBe312Files() {
		assertEquals(312, fileProvider.filesToLoad().size());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void listOfFileIsUnmodifiable() {
		fileProvider.filesToLoad().add("");
	}
}
