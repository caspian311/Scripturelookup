package net.todd.bible.scripturelookup.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class DataManagementView implements IDataManagementView {
	private final Button reloadButton;
	private final ListenerManager reloadButtonListenerManager = new ListenerManager();

	public DataManagementView() {
		reloadButton = new Button("Reload");
		reloadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				reloadButtonListenerManager.notifyListeners();
			}
		});
		
		RootPanel.get("reloadButtonContainer").add(reloadButton);
	}
	
	public void addReloadButtonListener(IListener listener) {
		reloadButtonListenerManager.addListener(listener);
	}
}
