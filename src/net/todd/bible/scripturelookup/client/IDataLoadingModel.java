package net.todd.bible.scripturelookup.client;

public interface IDataLoadingModel {
	void reloadData();

	void addDataReloadedListener(IListener successListener);

	void addFailureListener(IListener failureListener);

	String getErrorMessage();
}
