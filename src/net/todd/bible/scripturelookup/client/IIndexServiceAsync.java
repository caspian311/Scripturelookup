package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IIndexServiceAsync {
	void createIndex(AsyncCallback<String> callback);
}
