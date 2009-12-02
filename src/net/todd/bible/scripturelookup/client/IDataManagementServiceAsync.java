package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IDataManagementServiceAsync {
	void reload(String query, AsyncCallback<String> callback);
}
