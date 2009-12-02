package net.todd.bible.scripturelookup.client;

public interface IDataManagementModel {
	void reloadData();

	void addSuccessListener(IListener successListener);

	void addFailureListener(IListener failureListener);

	String getErrorMessage();
}
