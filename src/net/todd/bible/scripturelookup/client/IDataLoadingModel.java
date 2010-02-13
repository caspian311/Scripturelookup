package net.todd.bible.scripturelookup.client;

public interface IDataLoadingModel {
	void reloadData();

	void addDataReloadedListener(IListener successListener);

	String getErrorMessage();

	void addFailureListener(IListener failureListener);
}
