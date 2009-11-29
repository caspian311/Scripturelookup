package net.todd.bible.scripturelookup.client;

import java.util.ArrayList;
import java.util.List;

public class ListenerManager {
	List<IListener> listeners = new ArrayList<IListener>();
	
	public void addListener(IListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void notifyListeners() {
		for (IListener listener : listeners) {
			listener.handleEvent();
		}
	}
}
