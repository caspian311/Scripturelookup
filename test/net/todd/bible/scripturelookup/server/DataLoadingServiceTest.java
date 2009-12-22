package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

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
	public void deleteOldDataThenLoadInNew() {
		dataLoadingService.reload(null);
		
		InOrder inorder = inOrder(bibleDao, searchEngine);

		inorder.verify(bibleDao).deleteData();
		inorder.verify(bibleDao).loadData(any(InputStream.class));
		inorder.verify(searchEngine).createIndex();
	}
	
	@Test
	public void returnSuccessMessageWhenReloadSuccessful() {
		assertEquals("Success", dataLoadingService.reload(null));
	}
}
