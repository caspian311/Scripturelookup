package net.todd.bible.scripturelookup.server;

import net.todd.bible.scripturelookup.client.IDataManagementService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataManagementService extends RemoteServiceServlet implements IDataManagementService {
	private static final long serialVersionUID = 5244338505093814611L;
	private final IBibleDao bibleDao;

	public DataManagementService() {
		bibleDao = new BibleDao(PMF.get());
	}

	public DataManagementService(IBibleDao bibleDao) {
		this.bibleDao = bibleDao;
	}

	@Override
	public String reload(String query) {
		bibleDao.deleteData();
		bibleDao.loadData(null);
		return null;
	}
}
