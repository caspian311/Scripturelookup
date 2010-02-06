package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ILookupServiceAsync {
	void lookup(String query, String queryType, AsyncCallback<String> callback);
}
