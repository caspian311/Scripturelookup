package net.todd.bible.scripturelookup.server;

import net.todd.bible.scripturelookup.client.IDataLoadingService;
import net.todd.bible.scripturelookup.client.IIndexService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataLoadingService extends RemoteServiceServlet implements IDataLoadingService,
		IIndexService {
	private static final long serialVersionUID = 5244338505093814611L;
	private final IBibleDao bibleDao;

	public DataLoadingService() {
		bibleDao = new BibleDao(PMF.get());
	}

	public DataLoadingService(IBibleDao bibleDao) {
		this.bibleDao = bibleDao;
	}

	@Override
	public String reload(String query) {
		bibleDao.deleteData();
		bibleDao.loadData(getClass().getResourceAsStream("/data.txt"));
		return "Success";
	}

	@Override
	public String rebuildIndex(String request) {
		// TODO
		return null;
	}
}
