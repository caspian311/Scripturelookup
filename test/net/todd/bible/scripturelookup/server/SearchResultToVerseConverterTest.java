package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class SearchResultToVerseConverterTest {
	private ISearchResultToVerseConverter converter;

	@Before
	public void setUp() {
		converter = new SearchResultToVerseConverter();
	}
	
	@Test
	public void nullSearchResults() {
		List<Verse> convertedVerses = converter.convertToVerses(null);
		assertEquals(0, convertedVerses.size());
	}

	@Test
	public void emptySearchResults() {
		List<SearchResult> searchResults = Arrays.asList();
		
		List<Verse> convertedVerses = converter.convertToVerses(searchResults);
		assertEquals(0, convertedVerses.size());
	}
	
	@Test
	public void aFewSearchResults() {
		String book1 = UUID.randomUUID().toString();
		String chapter1 = UUID.randomUUID().toString();
		String verse1 = UUID.randomUUID().toString();
		String text1 = UUID.randomUUID().toString();

		SearchResult result1 = new SearchResult();
		result1.setBook(book1);
		result1.setChapter(chapter1);
		result1.setVerse(verse1);
		result1.setText(text1);
		
		String book2 = UUID.randomUUID().toString();
		String chapter2 = UUID.randomUUID().toString();
		String verse2 = UUID.randomUUID().toString();
		String text2 = UUID.randomUUID().toString();

		SearchResult result2 = new SearchResult();
		result2.setBook(book2);
		result2.setChapter(chapter2);
		result2.setVerse(verse2);
		result2.setText(text2);

		List<SearchResult> searchResults = Arrays.asList(result1, result2);

		List<Verse> convertedVerses = converter.convertToVerses(searchResults);
		assertEquals(2, convertedVerses.size());
		assertEquals(book1, convertedVerses.get(0).getBook());
		assertEquals(chapter1, convertedVerses.get(0).getChapter());
		assertEquals(verse1, convertedVerses.get(0).getVerse());
		assertEquals(text1, convertedVerses.get(0).getText());
		
		assertEquals(book2, convertedVerses.get(1).getBook());
		assertEquals(chapter2, convertedVerses.get(1).getChapter());
		assertEquals(verse2, convertedVerses.get(1).getVerse());
		assertEquals(text2, convertedVerses.get(1).getText());
	}
}
