package net.todd.bible.scripturelookup.client;

public interface IDataLoadingModel {
	void reloadData();

	void addIndexBuiltListener(IListener successListener);

	void addFailureListener(IListener failureListener);

	String getErrorMessage();

	void rebuildIndex();

	void addIndexBuildSuccessListener(IListener successListener);
}
