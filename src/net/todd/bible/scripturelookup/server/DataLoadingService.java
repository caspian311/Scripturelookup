package net.todd.bible.scripturelookup.server;

import net.todd.bible.scripturelookup.client.IDataDeletingService;
import net.todd.bible.scripturelookup.client.IDataLoadingService;
import net.todd.bible.scripturelookup.client.IIndexService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataLoadingService extends RemoteServiceServlet implements IDataDeletingService,
		IDataLoadingService, IIndexService {
	private static final long serialVersionUID = 5244338505093814611L;
	private final IBibleDao bibleDao;
	private final ISearchEngine searchEngine;

	public DataLoadingService() {
		bibleDao = BibleDaoProvider.getBibleDao();
		searchEngine = new SearchEngine(bibleDao);
	}

	public DataLoadingService(IBibleDao bibleDao, ISearchEngine searchEngine) {
		this.bibleDao = bibleDao;
		this.searchEngine = searchEngine;
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
	
	@Override
	public String createIndex() {
		searchEngine.createIndex();
		return "SUCCESS";
	}
}
