package net.todd.bible.scripturelookup.client;

public interface IDataManagementView {
	void addReloadButtonListener(IListener listener);

	void showErrorMessage(String errorMessage);

	void showSuccessMessage();
}
