package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IDataLoadingServiceAsync {
	void loadAllData(AsyncCallback<String> callback);
}
