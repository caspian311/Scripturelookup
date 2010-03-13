package net.todd.bible.scripturelookup.client.service;

import net.todd.bible.scripturelookup.client.IListener;

public interface IServiceCaller {
	void addSuccessListener(IListener listener);

	void addFailureListener(IListener listener);

	Throwable getException();

	String getReturnValue();
}