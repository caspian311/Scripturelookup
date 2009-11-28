package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ILookupServiceAsync {
	void lookup(String input, AsyncCallback<String> callback);
}
