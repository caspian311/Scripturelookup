package net.todd.bible.scripturelookup.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Scripturelookup implements EntryPoint {
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	private final Button sendButton;
	private final TextBox nameField;

	private final DialogBox dialogBox;
	private final Button closeButton;
	private final Label textToServerLabel;
	private final HTML serverResponseLabel;

	public Scripturelookup() {
		sendButton = new Button("Send");
		sendButton.addStyleName("sendButton");

		nameField = new TextBox();
		nameField.setText("GWT User");

		dialogBox = new DialogBox();
		closeButton = new Button("Close");
		textToServerLabel = new Label();
		serverResponseLabel = new HTML();
	}

	public void onModuleLoad() {
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);

		nameField.setFocus(true);
		nameField.selectAll();

		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);

		closeButton.getElement().setId("closeButton");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				closeDialog();
			}
		});

		sendButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}
		});

		nameField.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}
		});
	}

	private void showDialog() {
		dialogBox.center();
		closeButton.setFocus(true);
	}
	
	private void closeDialog() {
		dialogBox.hide();
		sendButton.setEnabled(true);
		sendButton.setFocus(true);
	}

	private void sendNameToServer() {
		sendButton.setEnabled(false);

		String textToServer = nameField.getText();

		textToServerLabel.setText(textToServer);

		greetingService.greetServer(textToServer, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				dialogBox.setText("Error!");
				serverResponseLabel.setHTML("Error: " + caught.getMessage());
				
				showDialog();
			}

			public void onSuccess(String result) {
				dialogBox.setText("Response");
				serverResponseLabel.setHTML(result);
				
				showDialog();
			}
		});
	}
}
