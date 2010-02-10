package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class DataLoadingServiceTest {
	private IDataLoader dataLoader;
	private DataLoadingService dataLoadingService;
	
	@Before
	public void setUp() {
		dataLoader = mock(IDataLoader.class);
		dataLoadingService = new DataLoadingService(dataLoader);
	}
	
	@Test
	public void deleteAllDataDeletesDataInDao() {
		dataLoadingService.deleteAllData();
		
		verify(dataLoader).deleteData();
	}
	
	@Test
	public void loadAllDataLoadsDataIntoDao() {
		dataLoadingService.loadAllData();

		verify(dataLoader).loadData(any(InputStream.class));
	}

	@Test
	public void allServiceCallsReturnSUCCESSWhenSuccessful() {
		assertEquals("SUCCESS", dataLoadingService.deleteAllData());
		assertEquals("SUCCESS", dataLoadingService.loadAllData());
	}
}
