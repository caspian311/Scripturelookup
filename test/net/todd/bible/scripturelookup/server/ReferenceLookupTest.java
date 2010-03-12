package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.todd.bible.scripturelookup.server.ReferenceParser.Reference;

import org.junit.Before;
import org.junit.Test;

public class ReferenceLookupTest {
	private IReferenceLookup referenceLookup;
	private IBibleDao bibleDao;
	private Reference reference;
	private String book;
	private List<Integer> chapters;
	private List<Integer> verses;

	@Before
	public void setUp() {
		bibleDao = mock(IBibleDao.class);
		referenceLookup = new ReferenceLookup(bibleDao);

		reference = mock(Reference.class);
		
		book = UUID.randomUUID().toString();
		doReturn(book).when(reference).getBook();

		chapters = Arrays.asList(1, 2, 3);
		doReturn(chapters).when(reference).getChapters();

		verses = Arrays.asList(4, 5, 6);
		doReturn(verses).when(reference).getVerses();
	}

	@Test
	public void returnWhateverDaoReturns() {
		List<Verse> results = Arrays.asList();
		doReturn(results).when(bibleDao).getAllSpecifiedVerses(anyString(),
				anyListOf(Integer.class), anyListOf(Integer.class));

		List<Verse> actualResults = referenceLookup.lookupReference(reference);

		assertEquals(results, actualResults);
	}

	@Test
	public void daoGetsBookChaptersAndVersesFromReference() {
		referenceLookup.lookupReference(reference);

		verify(bibleDao).getAllSpecifiedVerses(book, chapters, verses);
	}
}
