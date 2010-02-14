package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.InputStream;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class DataLoadingServiceTest {
	private IDataLoader dataLoader;
	private DataLoadingService dataLoadingService;
	private String fileName;
	private IDataFileStreamer dataFileStreamer;
	
	@Before
	public void setUp() {
		dataLoader = mock(IDataLoader.class);
		dataFileStreamer = mock(IDataFileStreamer.class);
		dataLoadingService = new DataLoadingService(dataLoader, dataFileStreamer);
		
		fileName = UUID.randomUUID().toString();
	}
	
	@Test
	public void deleteAllDataDeletesDataInDao() {
		dataLoadingService.deleteAllData();
		
		verify(dataLoader).deleteData();
	}
	
	@Test
	public void fetchStreamForDataFileSpecifiedByThePart() {
		dataLoadingService.loadAllData(fileName);

		verify(dataFileStreamer).getStreamForFile(fileName);
	}

	@Test
	public void loadAllDataFromDataFileStream() {
		InputStream inputStream = mock(InputStream.class);
		doReturn(inputStream).when(dataFileStreamer).getStreamForFile(anyString());

		dataLoadingService.loadAllData(fileName);

		verify(dataLoader).loadData(inputStream);
	}

	@Test
	public void deleteAllDataReturnSUCCESSWhenSuccessful() {
		assertEquals("SUCCESS", dataLoadingService.deleteAllData());
	}

	@Test
	public void loadAllDataReturnSUCCESSWhenSuccessful() {
		assertEquals("SUCCESS", dataLoadingService.loadAllData(fileName));
	}
}
