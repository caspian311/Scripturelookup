package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import java.io.InputStream;

import net.todd.bible.scripturelookup.client.IDataManagementService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class DataManagementServiceTest {
	private IBibleDao bibleDao;
	private IDataManagementService dataManagementService;
	
	@Before
	public void setUp() {
		bibleDao = mock(IBibleDao.class);
		dataManagementService = new DataManagementService(bibleDao);
	}
	
	@Test
	public void deleteOldDataThenLoadInNew() {
		dataManagementService.reload(null);
		
		InOrder inorder = inOrder(bibleDao);

		inorder.verify(bibleDao).deleteData();
		inorder.verify(bibleDao).loadData(any(InputStream.class));
	}
	
	@Test
	public void returnSuccessMessageWhenSuccessful() {
		assertEquals("Success", dataManagementService.reload(null));
	}
}
