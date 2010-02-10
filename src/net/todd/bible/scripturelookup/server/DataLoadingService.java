package net.todd.bible.scripturelookup.server;

import net.todd.bible.scripturelookup.client.IDataDeletingService;
import net.todd.bible.scripturelookup.client.IDataLoadingService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataLoadingService extends RemoteServiceServlet implements IDataDeletingService,
		IDataLoadingService {
	private static final long serialVersionUID = 5244338505093814611L;
	private final IDataLoader dataLoader;

	public DataLoadingService() {
		dataLoader = DataLoaderProvider.getDataLoader();
	}

	public DataLoadingService(IDataLoader dataLoader) {
		this.dataLoader = dataLoader;
	}

	@Override
	public String deleteAllData() {
		dataLoader.deleteData();
		return "SUCCESS";
	}

	@Override
	public String loadAllData() {
		dataLoader.loadData(getClass().getResourceAsStream("/data.txt"));
		return "SUCCESS";
	}
}
