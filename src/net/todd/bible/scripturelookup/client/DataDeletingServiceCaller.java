package net.todd.bible.scripturelookup.client;

public class DataDeletingServiceCaller extends ServiceCaller implements IDataDeletingServiceCaller {
	private final IDataDeletingServiceAsync service;

	public DataDeletingServiceCaller(IDataDeletingServiceAsync service) {
		this.service = service;
	}

	@Override
	public void callService() {
		service.deleteAllData(getCallback());
	}
}
