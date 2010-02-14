package net.todd.bible.scripturelookup.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class DataManagementView implements IDataManagementView {
	private final ListenerManager deleteButtonListenerManager = new ListenerManager();
	private final ListenerManager reloadButtonListenerManager = new ListenerManager();

	private static final String DELETION_SUCCESS_MESSAGE = "Data has deleted successfully.";
	private static final String RELOAD_SUCCESS_MESSAGE = "Data has reloaded successfully.";
	
	private static final String DELETION_BUSY_MESSAGE = "Deleting data... please wait...";
	private static final String RELOAD_BUSY_MESSAGE = "Reloading data... please wait...";

	private final Button reloadButton;
	private final Button deleteButton;

	private final Label messageLabel;

	public DataManagementView() {
		messageLabel = new Label();
		deleteButton = new Button("Delete");
		reloadButton = new Button("Reload");

		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deleteButtonListenerManager.notifyListeners();
			}
		});

		reloadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				reloadButtonListenerManager.notifyListeners();
			}
		});

		RootPanel.get("deleteButtonContainer").add(deleteButton);
		RootPanel.get("reloadButtonContainer").add(reloadButton);
		RootPanel.get("statusMessage").add(messageLabel);
	}

	@Override
	public void addReloadButtonListener(IListener listener) {
		reloadButtonListenerManager.addListener(listener);
	}

	@Override
	public void addDeleteButtonListener(IListener listener) {
		deleteButtonListenerManager.addListener(listener);
	}

	@Override
	public void showErrorMessage(String errorMessage) {
		messageLabel.setText(errorMessage);
	}

	@Override
	public void showDeletionSuccessMessage() {
		messageLabel.setText(DELETION_SUCCESS_MESSAGE);
	}

	@Override
	public void showReloadSuccessMessage() {
		messageLabel.setText(RELOAD_SUCCESS_MESSAGE);
	}

	@Override
	public void showDeletingBusySignal() {
		messageLabel.setText(DELETION_BUSY_MESSAGE);
	}

	@Override
	public void showReloadingBusySignal() {
		messageLabel.setText(RELOAD_BUSY_MESSAGE);
	}

	@Override
	public void updatePercentComplete(double percentComplete) {
		messageLabel.setText("Updating: " + percentComplete * 100 + "% complete...");
	}
}
