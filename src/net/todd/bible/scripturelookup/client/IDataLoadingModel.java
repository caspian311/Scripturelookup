package net.todd.bible.scripturelookup.client;

public interface IDataLoadingModel {
	void deleteData();

	void addDataDeletionListener(IListener listener);

	void reloadData();

	void addDataReloadedListener(IListener successListener);

	void createIndex();

	void addIndexCreatedListener(IListener successListener);

	String getErrorMessage();

	void addFailureListener(IListener failureListener);
}
