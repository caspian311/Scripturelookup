package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SearchEngineTest {
	private ISearchEngine searchEngine;

	@Before
	public void setUp() {
		List<Verse> verses = new ArrayList<Verse>();
		verses.add(new Verse("", "", "", "quick brown fox"));
		verses.add(new Verse("", "", "", "lazy dog"));
		verses.add(new Verse("", "", "", "dog eat dog"));
		verses.add(new Verse("", "", "", "foxxy fox"));

		IBibleDao bibleDao = mock(IBibleDao.class);
		when(bibleDao.getAllVerses()).thenReturn(verses);

		searchEngine = new SearchEngine(bibleDao);
	}

	@Test
	public void searchResultsAreRelavent() {
		searchEngine.createIndex();
		List<SearchResult> results = searchEngine.search("fox");

		assertTrue(containsText(results, "foxxy fox"));
		assertTrue(containsText(results, "quick brown fox"));
	}

	private boolean containsText(List<SearchResult> results, String text) {
		boolean doesItContainTheText = false;

		for (SearchResult result : results) {
			if (result.getText().equals(text)) {
				doesItContainTheText = true;
				break;
			}
		}

		return doesItContainTheText;
	}

	@Test
	public void searchResultsAreOrdered() {
		searchEngine.createIndex();
		List<SearchResult> results = searchEngine.search("dog");

		assertEquals("dog eat dog", results.get(0).getText());
		assertEquals("lazy dog", results.get(1).getText());
	}
}
