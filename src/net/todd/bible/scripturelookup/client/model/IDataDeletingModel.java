package net.todd.bible.scripturelookup.client.model;

import net.todd.bible.scripturelookup.client.IListener;

public interface IDataDeletingModel {
	void deleteData();

	void addFailureListener(IListener listener);

	void addDataDeletionListener(IListener listener);

	String getErrorMessage();
}
