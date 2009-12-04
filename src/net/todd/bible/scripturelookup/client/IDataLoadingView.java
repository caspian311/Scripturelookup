package net.todd.bible.scripturelookup.client;

public interface IDataLoadingView {
	void addReloadButtonListener(IListener listener);

	void showErrorMessage(String errorMessage);

	void showSuccessMessage();

	void showBusySignal();
}
