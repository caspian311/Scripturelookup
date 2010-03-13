package net.todd.bible.scripturelookup.client.service;

public interface ILookupServiceCaller extends IServiceCaller {
	void callService(String queryType, String query);
}