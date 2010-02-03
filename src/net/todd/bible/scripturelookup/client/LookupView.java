package net.todd.bible.scripturelookup.client;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

public class LookupView implements ILookupView {
	private final Button submitButton;
	private final TextBox queryField;

	private final HTML serverResponseLabel;
	private final SimplePanel responsePanel;
	private final ListBox queryTypePanel;

	private final ListenerManager submissionListeners = new ListenerManager();
	private final ListenerManager queryTypeChangedListener = new ListenerManager();

	public LookupView() {
		submitButton = new Button("Search");
		submitButton.addStyleName("sendButton");

		queryField = new TextBox();
		queryField.setName("query");
		queryField.setText("");
		queryField.setWidth("100%");

		responsePanel = new SimplePanel();
		serverResponseLabel = new HTML();

		queryTypePanel = new ListBox();
		queryTypePanel.setName("queryType");
		queryTypePanel.addItem("By Keyword", "keyword");
		queryTypePanel.addItem("By Reference", "reference");
		queryTypePanel.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				queryTypeChangedListener.notifyListeners();
			}
		});

		RootPanel.get("queryFieldContainer").add(queryField);
		RootPanel.get("submitButtonContainer").add(submitButton);
		RootPanel.get("responseContainer").add(responsePanel);
		RootPanel.get("queryTypeFieldContainer").add(queryTypePanel);

		queryField.setFocus(true);
		queryField.selectAll();

		responsePanel.add(serverResponseLabel);

		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				submissionListeners.notifyListeners();
			}
		});

		queryField.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					submissionListeners.notifyListeners();
				}
			}
		});
	}

	public void addQueryTypeChangeListener(IListener listener) {
		queryTypeChangedListener.addListener(listener);
	}

	public void addSubmissionListener(IListener listener) {
		submissionListeners.addListener(listener);
	}

	public void showVerses(List<Verse> verses) {
		StringBuffer response = new StringBuffer();
		for (Verse verse : verses) {
			response.append("<b>").append(verse.getBook()).append(" ").append(verse.getChapter())
					.append(":").append(verse.getVerse()).append(" - ").append("</b>");
			response.append(verse.getText());
			response.append("<br />\n");
		}
		serverResponseLabel.setHTML(response.toString());
	}

	public void showBusySignal() {
		serverResponseLabel.setHTML("Searching Scripture...");
	}

	@Override
	public void showErrorMessage(String errorMessage) {
		submitButton.setEnabled(true);
		serverResponseLabel.setHTML("Error: " + errorMessage);
	}

	public void showNoResultsMessage() {
		submitButton.setEnabled(true);
		serverResponseLabel.setHTML("No results found");
	}

	public void enableSubmitButton() {
		submitButton.setEnabled(true);
	}

	public void disableSubmitButton() {
		submitButton.setEnabled(false);
	}

	public String getQueryString() {
		return queryField.getText();
	}
}
