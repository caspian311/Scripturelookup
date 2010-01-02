package net.todd.bible.scripturelookup.client;

public interface IDataLoadingView {
	void addDeleteButtonListener(IListener listener);
	
	void addReloadButtonListener(IListener listener);

	void addReIndexButtonListener(IListener listener);
	
	void showErrorMessage(String errorMessage);

	void showSuccessMessage();

	void showBusySignal();
}
