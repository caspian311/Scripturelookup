package net.todd.bible.scripturelookup.server;

import net.todd.bible.scripturelookup.client.service.IDataDeletingService;
import net.todd.bible.scripturelookup.client.service.IDataLoadingService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataLoadingService extends RemoteServiceServlet implements IDataDeletingService,
		IDataLoadingService {
	private static final long serialVersionUID = 5244338505093814611L;
	private final IDataLoader dataLoader;
	private final IDataFileStreamer dataFileStreamer;

	public DataLoadingService() {
		dataLoader = DataLoaderProvider.getDataLoader();
		dataFileStreamer = new DataFileStreamer();
	}

	public DataLoadingService(IDataLoader dataLoader, IDataFileStreamer dataFileStreamer) {
		this.dataLoader = dataLoader;
		this.dataFileStreamer = dataFileStreamer;
	}

	@Override
	public String deleteAllData() {
		dataLoader.deleteData();
		return "SUCCESS";
	}

	@Override
	public String loadAllData(String dataFile) {
		dataLoader.loadData(dataFileStreamer.getStreamForFile(dataFile));
		return "SUCCESS";
	}
}
