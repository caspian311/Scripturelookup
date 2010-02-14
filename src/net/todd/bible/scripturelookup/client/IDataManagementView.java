package net.todd.bible.scripturelookup.client;

public interface IDataManagementView {
	void addDeleteButtonListener(IListener listener);
	
	void addReloadButtonListener(IListener listener);

	void showErrorMessage(String errorMessage);

	void showDeletionSuccessMessage();

	void showReloadSuccessMessage();

	void showDeletingBusySignal();

	void showReloadingBusySignal();

	void updatePercentComplete(double percentComplete);
}
