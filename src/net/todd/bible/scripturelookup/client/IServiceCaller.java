package net.todd.bible.scripturelookup.client;

public interface IServiceCaller {

	void callService(String input);

	void addSuccessListener(IListener listener);

	void addFailureListener(IListener listener);

	Throwable getException();

	String getReturnValue();

}