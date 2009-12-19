package net.todd.bible.scripturelookup.server;

import net.todd.bible.scripturelookup.client.IDataLoadingService;
import net.todd.bible.scripturelookup.client.IIndexService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataLoadingService extends RemoteServiceServlet implements IDataLoadingService,
		IIndexService {
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
	public String reload(String query) {
		bibleDao.deleteData();
		bibleDao.loadData(getClass().getResourceAsStream("/data.txt"));
		return "Success";
	}

	@Override
	public String rebuildIndex(String request) {
		searchEngine.deleteExistingIndex();
		searchEngine.createIndex();
		return "Success";
	}
}
