package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.UUID;

import net.todd.bible.scripturelookup.server.data.Verse;

import org.junit.Test;

public class VerseTest {
	@Test
	public void toStringReturnsFormattedVersionOfVerse() {
		String book = UUID.randomUUID().toString();
		Integer chapter = new Random().nextInt();
		Integer verse = new Random().nextInt();
		String text = UUID.randomUUID().toString();
		Verse verseBean = new Verse(book, chapter, verse, text);

		assertEquals(book + " " + chapter + ":" + verse + " - " + text, verseBean.toString());
	}
	
	@Test
	public void getReferenceReturnsWellFormattedBibleReference() {
		String book = UUID.randomUUID().toString();
		Integer chapter = new Random().nextInt();
		Integer verse = new Random().nextInt();
		Verse verseBean = new Verse(book, chapter, verse, null);

		assertEquals(book + " " + chapter + ":" + verse, verseBean.getReference());
	}
}
