package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import net.todd.bible.scripturelookup.server.ReferenceParser.Reference;

import org.junit.Before;
import org.junit.Test;

public class ReferenceLookupTest {
	private IReferenceLookup referenceLookup;
	private IBibleDao bibleDao;
	private Reference referenceToBookOfJohn;
	private Reference referenceToJohn3;
	private Reference referenceToJohn316;

	@Before
	public void setUp() {
		bibleDao = mock(IBibleDao.class);
		referenceLookup = new ReferenceLookup(bibleDao);

		referenceToBookOfJohn = mock(Reference.class);
		doReturn("john").when(referenceToBookOfJohn).getBook();
		doReturn(0).when(referenceToBookOfJohn).getChapter();
		doReturn(0).when(referenceToBookOfJohn).getVerse();
		
		referenceToJohn3 = mock(Reference.class);
		doReturn("john").when(referenceToJohn3).getBook();
		doReturn(3).when(referenceToJohn3).getChapter();
		doReturn(0).when(referenceToJohn3).getVerse();
		
		referenceToJohn316 = mock(Reference.class);
		doReturn("john").when(referenceToJohn316).getBook();
		doReturn(3).when(referenceToJohn316).getChapter();
		doReturn(16).when(referenceToJohn316).getVerse();
	}

	@Test
	public void lookupingUpABookGetsAllVersesWithTheTitleOfThatBook() {
		referenceLookup.lookupReference(referenceToBookOfJohn);
		
		verify(bibleDao).getAllVersesInBook("john");
	}
	
	@Test
	public void whenLookupingUpABookReturnWhateverDaoReturns() {
		List<Verse> results = Arrays.asList();
		doReturn(results).when(bibleDao).getAllVersesInBook(anyString());

		List<Verse> actualResults = referenceLookup.lookupReference(referenceToBookOfJohn);

		assertEquals(results, actualResults);
	}

	@Test
	public void lookupingUpAChapterGetsAllVersesWithinThatChapter() {
		referenceLookup.lookupReference(referenceToJohn3);

		verify(bibleDao).getAllVersesInChapter("john", 3);
	}
	
	@Test
	public void whenLookupingUpAChapterReturnWhateverDaoReturns() {
		List<Verse> results = Arrays.asList();
		doReturn(results).when(bibleDao).getAllVersesInChapter(anyString(), anyInt());

		List<Verse> actualResults = referenceLookup.lookupReference(referenceToJohn3);

		assertEquals(results, actualResults);
	}

	@Test
	public void lookupingUpAVerseGetsJustThatVerse() {
		referenceLookup.lookupReference(referenceToJohn316);

		verify(bibleDao).getVerse("john", 3, 16);
	}
	
	@Test
	public void whenLookupingUpAVeseReturnWhateverDaoReturns() {
		Verse verse = mock(Verse.class);
		doReturn(verse).when(bibleDao).getVerse(anyString(), anyInt(), anyInt());

		List<Verse> actualResults = referenceLookup.lookupReference(referenceToJohn316);

		assertEquals(1, actualResults.size());
		assertEquals(verse, actualResults.get(0));
	}
	
	@Test
	public void whenNothingComesBackFromDaoResultsShouldBeEmpty() {
		doReturn(null).when(bibleDao).getVerse(anyString(), anyInt(), anyInt());

		List<Verse> actualResults = referenceLookup.lookupReference(referenceToJohn316);
		assertTrue(actualResults.isEmpty());
	}
}
