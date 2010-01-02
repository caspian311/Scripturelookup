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

	private static final String SUCCESS_MESSAGE = "Data has reloaded successfully.";
	private static final String BUSY_MESSAGE = "Reloading data... please wait...";

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
	public void showSuccessMessage() {
		messageLabel.setText(SUCCESS_MESSAGE);
	}

	@Override
	public void showBusySignal() {
		messageLabel.setText(BUSY_MESSAGE);
	}
}
