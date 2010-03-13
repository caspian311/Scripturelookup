package net.todd.bible.scripturelookup.client.service;


public class LookupServiceCaller extends ServiceCaller implements ILookupServiceCaller {
	private final ILookupServiceAsync service;
	
	public LookupServiceCaller(ILookupServiceAsync service) {
		this.service = service;
	}

	public void callService(String queryType, String query) {
		service.lookup(queryType, query, getCallback());
	}
}
