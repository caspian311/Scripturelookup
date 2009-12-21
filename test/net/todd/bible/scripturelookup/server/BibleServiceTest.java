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
	public void searchReturnsConvertedResults() {
		String query = UUID.randomUUID().toString();

		SearchResult result1 = new SearchResult();
		result1.setText(UUID.randomUUID().toString());
		SearchResult result2 = new SearchResult();
		result2.setText(UUID.randomUUID().toString());
		SearchResult result3 = new SearchResult();
		result3.setText(UUID.randomUUID().toString());

		List<SearchResult> expectedResults = new ArrayList<SearchResult>();
		expectedResults.add(result1);
		expectedResults.add(result2);
		expectedResults.add(result3);

		when(searchEngine.search(query)).thenReturn(expectedResults);

		IBibleService bibleService = new BibleService(searchEngine);

		List<Verse> actualVerses = bibleService.search(query);

		assertEquals(3, actualVerses.size());
		assertSame(expectedResults.get(0).getText(), actualVerses.get(0).getText());
		assertSame(expectedResults.get(1).getText(), actualVerses.get(1).getText());
		assertSame(expectedResults.get(2).getText(), actualVerses.get(2).getText());
	}
}