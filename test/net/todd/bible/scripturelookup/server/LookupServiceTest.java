package net.todd.bible.scripturelookup.server;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
}
