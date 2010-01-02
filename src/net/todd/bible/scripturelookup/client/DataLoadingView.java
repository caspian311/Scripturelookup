package net.todd.bible.scripturelookup.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class DataLoadingView implements IDataLoadingView {
	private final ListenerManager deleteButtonListenerManager = new ListenerManager();
	private final ListenerManager reloadButtonListenerManager = new ListenerManager();
	private final ListenerManager reIndexButtonListenerManager = new ListenerManager();

	private static final String DELETION_SUCCESS_MESSAGE = "Data has deleted successfully.";
	private static final String RELOAD_SUCCESS_MESSAGE = "Data has reloaded successfully.";
	private static final String INDEX_SUCCESS_MESSAGE = "Index has successfully been built.";
	
	private static final String DELETION_BUSY_MESSAGE = "Deleting data... please wait...";
	private static final String RELOAD_BUSY_MESSAGE = "Reloading data... please wait...";
	private static final String INDEX_BUSY_MESSAGE = "Indexing data... please wait...";

	private final Button reloadButton;
	private final Button deleteButton;
	private final Button reIndexButton;

	private final Label messageLabel;

	public DataLoadingView() {
		messageLabel = new Label();
		deleteButton = new Button("Delete");
		reloadButton = new Button("Reload");
		reIndexButton = new Button("Re-Index");

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

		reIndexButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				reIndexButtonListenerManager.notifyListeners();
			}
		});

		RootPanel.get("deleteButtonContainer").add(deleteButton);
		RootPanel.get("reloadButtonContainer").add(reloadButton);
		RootPanel.get("reIndexButtonContainer").add(reIndexButton);
		RootPanel.get("statusMessage").add(messageLabel);
	}

	@Override
	public void addReloadButtonListener(IListener listener) {
		reloadButtonListenerManager.addListener(listener);
	}

	@Override
	public void addReIndexButtonListener(IListener listener) {
		reIndexButtonListenerManager.addListener(listener);
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
	public void showIndexSuccessMessage() {
		messageLabel.setText(INDEX_SUCCESS_MESSAGE);
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
	public void showIndexingBusySignal() {
		messageLabel.setText(INDEX_BUSY_MESSAGE);
	}
}
