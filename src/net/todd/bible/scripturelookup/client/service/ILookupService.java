package net.todd.bible.scripturelookup.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("lookup")
public interface ILookupService extends RemoteService {
	String lookup(String queryType, String query);
}
