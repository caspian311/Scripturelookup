package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class DataLoadingServiceTest {
	private IBibleDao bibleDao;
	private ISearchEngine searchEngine;
	private DataLoadingService dataLoadingService;
	
	@Before
	public void setUp() {
		bibleDao = mock(IBibleDao.class);
		searchEngine = mock(ISearchEngine.class);
		dataLoadingService = new DataLoadingService(bibleDao, searchEngine);
	}
	
	@Test
	public void deleteAllDataDeletesDataInDao() {
		dataLoadingService.deleteAllData();
		
		verify(bibleDao).deleteData();
	}
	
	@Test
	public void loadAllDataLoadsDataIntoDao() {
		dataLoadingService.loadAllData();

		verify(bibleDao).loadData(any(InputStream.class));
	}

	@Test
	public void createIndexCreatesNewIndexInSearchEngine() {
		dataLoadingService.createIndex();

		verify(searchEngine).createIndex();
	}

	@Test
	public void allServiceCallsReturnSUCCESSWhenSuccessful() {
		assertEquals("SUCCESS", dataLoadingService.deleteAllData());
		assertEquals("SUCCESS", dataLoadingService.loadAllData());
		assertEquals("SUCCESS", dataLoadingService.createIndex());
	}
}
