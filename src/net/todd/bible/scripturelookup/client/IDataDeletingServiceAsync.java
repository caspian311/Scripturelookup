package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IDataDeletingServiceAsync {
	void deleteAllData(AsyncCallback<String> callback);
}
