package net.todd.bible.scripturelookup.client;

public class DataLoadingServiceCaller extends ServiceCaller implements IDataLoadingServiceCaller {
	private final IDataLoadingServiceAsync service;

	public DataLoadingServiceCaller(IDataLoadingServiceAsync service) {
		this.service = service;
	}

	@Override
	public void callService(String part) {
		service.loadAllData(part, getCallback());
	}
}
