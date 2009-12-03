package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

public class VerseTest {
	@Test
	public void testToString() {
		String book = UUID.randomUUID().toString();
		String chapter = UUID.randomUUID().toString();
		String verse = UUID.randomUUID().toString();
		String text = UUID.randomUUID().toString();
		Verse verseBean = new Verse(book, chapter, verse, text);

		assertEquals(book + " " + chapter + ":" + verse + " - " + text, verseBean.toString());
	}
}
