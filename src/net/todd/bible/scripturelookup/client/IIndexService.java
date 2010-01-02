package net.todd.bible.scripturelookup.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("createIndex")
public interface IIndexService extends RemoteService {
	String createIndex();
}
