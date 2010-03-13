package net.todd.bible.scripturelookup.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("loadAllData")
public interface IDataLoadingService extends RemoteService {
	String loadAllData(String dataFile);
}
