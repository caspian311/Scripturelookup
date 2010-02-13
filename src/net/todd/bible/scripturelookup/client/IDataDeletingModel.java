package net.todd.bible.scripturelookup.client;

public interface IDataDeletingModel {
	void deleteData();

	void addFailureListener(IListener listener);

	void addDataDeletionListener(IListener listener);

	String getErrorMessage();
}
