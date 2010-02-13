package net.todd.bible.scripturelookup.client;

public interface ILookupServiceCaller extends IServiceCaller {
	void callService(String queryType, String query);
}