package net.todd.bible.scripturelookup.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

public class Scripturelookup implements EntryPoint {
	private final ILookupServiceAsync lookupService = GWT.create(ILookupService.class);

	private final Button submitButton;
	private final TextBox nameField;

	private final HTML serverResponseLabel;
	private final SimplePanel responsePanel;

	public Scripturelookup() {
		submitButton = new Button("Search");
		submitButton.addStyleName("sendButton");

		nameField = new TextBox();
		nameField.setText("");
		nameField.setWidth("100%");

		responsePanel = new SimplePanel();
		serverResponseLabel = new HTML();
	}

	public void onModuleLoad() {
		RootPanel.get("queryFieldContainer").add(nameField);
		RootPanel.get("submitButtonContainer").add(submitButton);
		RootPanel.get("responseContainer").add(responsePanel);

		nameField.setFocus(true);
		nameField.selectAll();

		responsePanel.add(serverResponseLabel);

		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				queryServer();
			}
		});

		nameField.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					queryServer();
				}
			}
		});
	}

	private void queryServer() {
		submitButton.setEnabled(false);
		serverResponseLabel.setHTML("Searching Scripture...");

		String textToServer = nameField.getText();

		lookupService.lookup(textToServer, new AsyncCallback<String>() {
			public void onFailure(Throwable error) {
				submitButton.setEnabled(true);
				serverResponseLabel.setHTML("Error: " + error.getMessage());
			}

			public void onSuccess(String response) {
				JSONValue parsedValue = JSONParser.parse(response);

				submitButton.setEnabled(true);
				popuplateSearchResults(parsedValue);
			}
		});
	}

	private void popuplateSearchResults(JSONValue parsedValue) {
		StringBuffer response = new StringBuffer();
		if (parsedValue.isArray() != null) {
			JSONArray responseArray = parsedValue.isArray();
			for (int i = 0; i < responseArray.size(); i++) {
				JSONValue value = responseArray.get(i);
				Verse verse = new Verse(value.isObject());
				response.append(verse.getBook()).append(" ").append(verse.getChapter()).append(":")
						.append(verse.getVerse()).append(" - ");
				response.append(verse.getText());
				response.append("<br />\n");
			}
		}
		serverResponseLabel.setText(response.toString());
	}
}
