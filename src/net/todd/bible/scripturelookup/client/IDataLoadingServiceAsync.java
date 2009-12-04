package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IDataLoadingServiceAsync {
	void reload(String query, AsyncCallback<String> callback);
}
