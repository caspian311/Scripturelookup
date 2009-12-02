package net.todd.bible.scripturelookup.server;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import java.io.InputStream;

import org.junit.Test;
import org.mockito.InOrder;

public class DataManagementServiceTest {
	@Test
	public void deleteOldDataThenLoadInNew() {
		IBibleDao bibleDao = mock(IBibleDao.class);

		new DataManagementService(bibleDao).reload(null);
		
		InOrder inorder = inOrder(bibleDao);

		inorder.verify(bibleDao).deleteData();
		inorder.verify(bibleDao).loadData(any(InputStream.class));
	}
}
