package net.todd.bible.scripturelookup.client;

public interface IServiceCaller {
	void addSuccessListener(IListener listener);

	void addFailureListener(IListener listener);

	Throwable getException();

	String getReturnValue();
}