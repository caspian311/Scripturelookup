package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class BibleServiceTest {
	private ISearchEngine searchEngine;

	@Before
	public void setUp() {
		searchEngine = mock(ISearchEngine.class);
	}

	@Test
	public void searchReturnsWhatDaoGaveIt() {
		String query = UUID.randomUUID().toString();
		
		List<Verse> expectedVerses = new ArrayList<Verse>();
		expectedVerses.add(new Verse("", "", "", ""));
		expectedVerses.add(new Verse("", "", "", ""));
		expectedVerses.add(new Verse("", "", "", ""));
		
		when(searchEngine.search(query)).thenReturn(expectedVerses);

		IBibleService bibleService = new BibleService(searchEngine);

		List<Verse> actualVerses = bibleService.search(query);

		assertEquals(3, actualVerses.size());
		assertSame(expectedVerses.get(0), actualVerses.get(0));
		assertSame(expectedVerses.get(1), actualVerses.get(1));
		assertSame(expectedVerses.get(2), actualVerses.get(2));
	}
}