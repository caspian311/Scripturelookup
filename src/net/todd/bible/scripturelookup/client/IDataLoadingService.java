package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("reload")
public interface IDataLoadingService extends RemoteService {
	String reload(String request);
}
