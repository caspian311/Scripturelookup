package net.todd.bible.scripturelookup.client;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DataManagementModelTest {
	@SuppressWarnings("unchecked")
	@Test
	public void callDataManagementServicewhenReloading() {
		IDataManagementServiceAsync dataManagementService = mock(IDataManagementServiceAsync.class);
		
		new DataManagementModel(dataManagementService).reloadData();
		
		verify(dataManagementService).reload(anyString(), (AsyncCallback) anyObject());
	}
}
