package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.todd.bible.scripturelookup.server.ReferenceParser.Reference;

import org.junit.Before;
import org.junit.Test;

public class ReferenceParserTest {
	private IReferenceParser referenceParser;

	@Before
	public void setUp() {
		referenceParser = new ReferenceParser();
	}
	
	@Test(expected = ReferenceParsingException.class)
	public void nullStringIsNotAReference() {
		referenceParser.parseReference(null);
	}

	@Test(expected = ReferenceParsingException.class)
	public void emptyStringIsNotAReference() {
		referenceParser.parseReference("");
	}
	
	@Test
	public void singleStringIsABookReference() {
		Reference reference = referenceParser.parseReference("test");

		assertEquals("test", reference.getBook());
	}
	
	@Test
	public void booksWithMultipleWordsIsAValidReference() {
		Reference reference = referenceParser.parseReference("test test");

		assertEquals("test test", reference.getBook());
	}
	
	@Test
	public void aSingleWordAndThenANumberIsAReference() {
		Reference reference = referenceParser.parseReference("test 1");

		assertEquals("test", reference.getBook());
		assertEquals(1, reference.getChapter().intValue());
	}
	
	@Test
	public void aSingleWordReferenceHas0ForAChapter() {
		Reference reference = referenceParser.parseReference("test");

		assertEquals(0, reference.getChapter().intValue());
	}
	
	@Test
	public void aSingleWordWithChapterAndVerseIsAReference() {
		Reference reference = referenceParser.parseReference("test 1:1");

		assertEquals("test", reference.getBook());
		assertEquals(1, reference.getChapter().intValue());
		assertEquals(1, reference.getVerse().intValue());
	}
	
	@Test
	public void aSingleWordReferenceHas0ForAVerse() {
		Reference reference = referenceParser.parseReference("test");

		assertEquals(0, reference.getVerse().intValue());
	}
	
	@Test
	public void aSingleWordAndANumberReferenceHas0ForAVerse() {
		Reference reference = referenceParser.parseReference("test 1");

		assertEquals(0, reference.getVerse().intValue());
	}
	
	@Test(expected = ReferenceParsingException.class)
	public void aSingleWordWithALetterForAChapterAndAColonIsNotAReference() {
		referenceParser.parseReference("test a:1");
	}

	@Test(expected = ReferenceParsingException.class)
	public void aSingleWordWithALetterForAverseAndAColonIsNotAReference() {
		referenceParser.parseReference("test 1:b");
	}
	
	@Test
	public void aNumberThenAWordIsAReference() {
		Reference reference = referenceParser.parseReference("1 test");

		assertEquals("1 test", reference.getBook());
	}
	
	@Test
	public void aNumberThenAWordThenAnotherNumberIsAReference() {
		Reference reference = referenceParser.parseReference("1 test 1");

		assertEquals(1, reference.getChapter().intValue());
	}

	@Test
	public void aNumberThenAWordThenAnotherNumberColonAndANumberIsAReference() {
		Reference reference = referenceParser.parseReference("1 test 1:1");

		assertEquals(1, reference.getVerse().intValue());
	}
	
	@Test
	public void rangeOfVerses() {
		Reference reference = referenceParser.parseReference("1 test 1:1-2");

		assertEquals(2, reference.getVerses().size());
		assertEquals(1, reference.getVerses().get(0).intValue());
		assertEquals(2, reference.getVerses().get(1).intValue());
	}
	
	@Test
	public void rangeOfChapters() {
		Reference reference = referenceParser.parseReference("1 test 1-2");

		assertEquals(2, reference.getChapters().size());
		assertEquals(1, reference.getChapters().get(0).intValue());
		assertEquals(2, reference.getChapters().get(1).intValue());
	}
	
	@Test
	public void chaptersAreEmptyWhenReferenceIsToABook() {
		Reference reference = referenceParser.parseReference("test");

		assertTrue(reference.getChapters().isEmpty());
	}
	
	@Test
	public void versesAreNullWhenReferenceIsToABookAndChapter() {
		Reference reference = referenceParser.parseReference("test 1");

		assertTrue(reference.getVerses().isEmpty());
	}
}
