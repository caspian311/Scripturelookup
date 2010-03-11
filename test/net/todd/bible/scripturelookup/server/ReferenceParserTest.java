package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
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
		assertEquals(1, reference.getChapter());
	}
	
	@Test
	public void aSingleWordReferenceHas0ForAChapter() {
		Reference reference = referenceParser.parseReference("test");

		assertEquals(0, reference.getChapter());
	}
	
	@Test
	public void aSingleWordWithChapterAndVerseIsAReference() {
		Reference reference = referenceParser.parseReference("test 1:1");

		assertEquals("test", reference.getBook());
		assertEquals(1, reference.getChapter());
		assertEquals(1, reference.getVerse());
	}
	
	@Test
	public void aSingleWordReferenceHas0ForAVerse() {
		Reference reference = referenceParser.parseReference("test");

		assertEquals(0, reference.getVerse());
	}
	
	@Test
	public void aSingleWordAndANumberReferenceHas0ForAVerse() {
		Reference reference = referenceParser.parseReference("test 1");

		assertEquals(0, reference.getVerse());
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

		assertEquals(1, reference.getChapter());
	}

	@Test
	public void aNumberThenAWordThenAnotherNumberColonAndANumberIsAReference() {
		Reference reference = referenceParser.parseReference("1 test 1:1");

		assertEquals(1, reference.getVerse());
	}
	
	@Test
	public void rangeOfVerses() {
		Reference reference = referenceParser.parseReference("1 test 1:1-2");

		assertEquals(2, reference.getVerses().length);
		assertEquals(1, reference.getVerses()[0]);
		assertEquals(2, reference.getVerses()[1]);
	}
	
	@Test
	public void rangeOfChapters() {
		Reference reference = referenceParser.parseReference("1 test 1-2");

		assertEquals(2, reference.getChapters().length);
		assertEquals(1, reference.getChapters()[0]);
		assertEquals(2, reference.getChapters()[1]);
	}
}
