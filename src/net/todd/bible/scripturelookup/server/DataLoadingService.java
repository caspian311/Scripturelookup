package net.todd.bible.scripturelookup.server;

import net.todd.bible.scripturelookup.client.IDataDeletingService;
import net.todd.bible.scripturelookup.client.IDataLoadingService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataLoadingService extends RemoteServiceServlet implements IDataDeletingService,
		IDataLoadingService {
	private static final long serialVersionUID = 5244338505093814611L;
	private final IBibleDao bibleDao;

	public DataLoadingService() {
		bibleDao = BibleDaoProvider.getBibleDao();
	}

	public DataLoadingService(IBibleDao bibleDao) {
		this.bibleDao = bibleDao;
	}

	@Override
	public String deleteAllData() {
		bibleDao.deleteData();
		return "SUCCESS";
	}

	@Override
	public String loadAllData() {
		bibleDao.loadData(getClass().getResourceAsStream("/data.txt"));
		return "SUCCESS";
	}
}
