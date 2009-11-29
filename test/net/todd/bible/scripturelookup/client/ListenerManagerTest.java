package net.todd.bible.scripturelookup.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class ListenerManagerTest {
	@Test
	public void test() {
		IListener listener1 = mock(IListener.class);
		IListener listener2 = mock(IListener.class);

		ListenerManager listenerManager = new ListenerManager();
		listenerManager.addListener(listener1);
		listenerManager.addListener(listener2);
		listenerManager.addListener(listener2);
		
		listenerManager.notifyListeners();

		verify(listener1).handleEvent();
		verify(listener2).handleEvent();
	}
}
