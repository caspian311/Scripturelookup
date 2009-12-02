package net.todd.bible.scripturelookup.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class DataManagementView implements IDataManagementView {
	private static final String SUCCESS_MESSAGE = "Data has reloaded successfully.";
	private final Button reloadButton;
	private final ListenerManager reloadButtonListenerManager = new ListenerManager();
	private final Label messageLabel;

	public DataManagementView() {
		messageLabel = new Label();
		reloadButton = new Button("Reload");
		reloadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				reloadButtonListenerManager.notifyListeners();
			}
		});
		
		RootPanel.get("reloadButtonContainer").add(reloadButton);
		RootPanel.get("statusMessage").add(messageLabel);
	}
	
	public void addReloadButtonListener(IListener listener) {
		reloadButtonListenerManager.addListener(listener);
	}

	@Override
	public void showErrorMessage(String errorMessage) {
		messageLabel.setText(errorMessage);
	}

	@Override
	public void showSuccessMessage() {
		messageLabel.setText(SUCCESS_MESSAGE);
	}
}
