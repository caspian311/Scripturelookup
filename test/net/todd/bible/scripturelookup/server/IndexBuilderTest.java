package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IndexBuilderTest {
	private String inexLocation;
	private IBibleDao bibleDao;
	private IIndexBuilder indexBuilder;

	@Before
	public void setUp() throws IOException {
		File tempFile = File.createTempFile(getClass().getName(), "");
		inexLocation = tempFile.getAbsolutePath();
		tempFile.delete();

		bibleDao = mock(IBibleDao.class);

		indexBuilder = new IndexBuilder(bibleDao);
	}

	@After
	public void tearDown() throws IOException {
		FileUtils.forceDelete(new File(inexLocation));
	}

	@Test
	public void creatingAnIndexCreatesADirectoryAtTheGivenLocation() {
		indexBuilder.createIndex(inexLocation);

		assertTrue(new File(inexLocation).exists());
		assertTrue(new File(inexLocation).isDirectory());
	}

	@Test
	public void creatingAnIndexPullsAllVersesFromDao() {
		indexBuilder.createIndex(inexLocation);

		verify(bibleDao, times(1)).getAllVerses();
	}
}
