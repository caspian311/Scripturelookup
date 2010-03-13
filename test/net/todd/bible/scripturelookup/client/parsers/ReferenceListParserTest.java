package net.todd.bible.scripturelookup.client.parsers;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.todd.bible.scripturelookup.client.Verse;
import net.todd.bible.scripturelookup.client.parsers.IReferenceListParser;
import net.todd.bible.scripturelookup.client.parsers.ReferenceListParser;

import org.junit.Ignore;
import org.junit.Test;

public class ReferenceListParserTest {
	@Test
	@Ignore
	// ignored because the stupid unsatisifedlinkerror
	public void searchResultAreAvailableWhenCallReturns() {
		IReferenceListParser parser = new ReferenceListParser();
		List<Verse> searchResults = parser
				.parse("[{\"book\":\"bookValue1\", \"chapter\":\"chapterValue1\", \"verse\":\"verseValue1\", \"text\":\"textValue1\"}]");

		assertEquals(1, searchResults.size());
		assertEquals("bookValue1", searchResults.get(0).getBook());
		assertEquals("chapterValue1", searchResults.get(0).getChapter());
		assertEquals("verseValue1", searchResults.get(0).getVerse());
		assertEquals("bookText1", searchResults.get(0).getText());
	}
}
