package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IIndexServiceAsync {
	void rebuildIndex(String request, AsyncCallback<String> callback);
}
