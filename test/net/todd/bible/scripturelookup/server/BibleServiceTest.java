package net.todd.bible.scripturelookup.server;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class BibleServiceTest {
	private IBibleDao bibleDao;

	@Before
	public void setUp() {
		bibleDao = mock(IBibleDao.class);
	}

	@Test
	public void test() {
		new BibleService(bibleDao);
	}
}
