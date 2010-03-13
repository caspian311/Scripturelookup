package net.todd.bible.scripturelookup.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("deleteAllData")
public interface IDataDeletingService extends RemoteService {
	String deleteAllData();
}
