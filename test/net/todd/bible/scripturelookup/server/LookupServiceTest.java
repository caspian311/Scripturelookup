package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class LookupServiceTest {
	private IBibleService bibleService;
	private LookupService lookupService;

	@Before
	public void setUp() {
		bibleService = mock(IBibleService.class);
		lookupService = new LookupService(bibleService);
	}
	
	@Test
	public void loadDatabaseOnServletInit() throws Exception {
		lookupService.init();

		verify(bibleService).loadDatabase();
		verify(bibleService).buildIndex();
	}

	@Test
	public void lookupSearchBibleService() throws Exception {
		String query = UUID.randomUUID().toString();

		lookupService.lookup(query);

		verify(bibleService, times(1)).search(query);
	}
	
	@Test
	public void noServiceCallIfEmptyQueryGiven() throws Exception {
		String query = "";

		lookupService.lookup(query);

		verify(bibleService, times(0)).search(query);
	}
	
	@Test
	public void noServiceCallIfNullQueryGiven() throws Exception {
		String query = null;

		lookupService.lookup(query);

		verify(bibleService, times(0)).search(query);
	}

	@Test
	public void lookupResultsAreInJSONFormat() throws Exception {
		String query = "test";
		
		List<Verse> verses = new ArrayList<Verse>();
		Verse verse1 = new Verse("bookValue1", "chapterValue1", "verseValue1", "textValue1");
		Verse verse2 = new Verse("bookValue2", "chapterValue2", "verseValue2", "textValue2");
		verses.add(verse1);
		verses.add(verse2);
		
		when(bibleService.search(query)).thenReturn(verses);
		
		String returnValue = lookupService.lookup(query);
		
		assertEquals(
				"[{\"book\":\"bookValue1\",\"chapter\":\"chapterValue1\",\"verse\":\"verseValue1\",\"text\":\"textValue1\"},{\"book\":\"bookValue2\",\"chapter\":\"chapterValue2\",\"verse\":\"verseValue2\",\"text\":\"textValue2\"}]",
				returnValue);
		
	}
}
