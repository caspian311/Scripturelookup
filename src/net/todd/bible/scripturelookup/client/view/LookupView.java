package net.todd.bible.scripturelookup.client.view;

import java.util.List;

import net.todd.bible.scripturelookup.client.IListener;
import net.todd.bible.scripturelookup.client.ListenerManager;
import net.todd.bible.scripturelookup.client.SearchResultsMetaData;
import net.todd.bible.scripturelookup.client.Verse;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

public class LookupView implements ILookupView {
	private final Button submitButton;
	private final TextBox queryField;

	private final SimplePanel responseMetaDataPanel;
	private final HTML responseLabel;
	private final SimplePanel resultsPanel;
	private final DataGridWidget resultsTable;
	private final ListBox queryTypeList;

	private final ListenerManager submissionListeners = new ListenerManager();
	private final ListenerManager queryTypeChangedListener = new ListenerManager();

	public LookupView() {
		submitButton = new Button("Search");
		submitButton.addStyleName("sendButton");

		queryField = new TextBox();
		queryField.setName("query");
		queryField.setText("");
		queryField.setWidth("100%");
		queryField.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					submissionListeners.notifyListeners();
				}
			}
		});

		responseMetaDataPanel = new SimplePanel();
		responseLabel = new HTML();

		resultsPanel = new SimplePanel();
		resultsTable = new DataGridWidget();
		resultsTable.setStyleName("searchResults");
		resultsTable.getColumnFormatter().setWidth(0, "200px");
		resultsTable.getColumnFormatter().setWidth(1, "400px");
		resultsTable.setHeader(0, "Reference");
		resultsTable.setHeader(1, "Text");

		queryTypeList = new ListBox();
		queryTypeList.setName("queryType");
		queryTypeList.addItem("By Keyword", "keyword");
		queryTypeList.addItem("By Reference", "reference");
		queryTypeList.setSelectedIndex(0);
		queryTypeList.setItemSelected(0, true);
		queryTypeList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				queryTypeChangedListener.notifyListeners();
			}
		});

		queryField.setFocus(true);
		queryField.selectAll();

		responseMetaDataPanel.add(responseLabel);
		resultsPanel.add(resultsTable);

		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				submissionListeners.notifyListeners();
			}
		});

		RootPanel.get("queryFieldContainer").add(queryField);
		RootPanel.get("submitButtonContainer").add(submitButton);
		RootPanel.get("responseContainer").add(responseMetaDataPanel);
		RootPanel.get("responseContainer").add(resultsPanel);
		RootPanel.get("queryTypeFieldContainer").add(queryTypeList);
	}

	@Override
	public void addQueryTypeChangeListener(IListener listener) {
		queryTypeChangedListener.addListener(listener);
	}

	@Override
	public void addSubmissionListener(IListener listener) {
		submissionListeners.addListener(listener);
	}

	@Override
	public void showMetaData(SearchResultsMetaData metaData) {
		responseLabel.setHTML(metaData.toString());
	}

	@Override
	public void showVerses(List<Verse> verses) {
		for (int i = 0; i < verses.size(); i++) {
			Verse verse = verses.get(i);
			String reference = verse.getBook() + " " + verse.getChapter() + ":" + verse.getVerse();
			if (i % 2 == 0) {
				resultsTable.getRowFormatter().setStyleName(i, "alternate");
			}
			resultsTable.setText(i, 0, reference);
			resultsTable.getCellFormatter().setVerticalAlignment(i, 0,
					HasVerticalAlignment.ALIGN_TOP);
			
			resultsTable.setText(i, 1, verse.getText());
			resultsTable.getCellFormatter().setVerticalAlignment(i, 1,
					HasVerticalAlignment.ALIGN_TOP);
			resultsTable.getCellFormatter().setHorizontalAlignment(i, 1,
					HasHorizontalAlignment.ALIGN_LEFT);
		}
	}

	@Override
	public void clearResponseLabel() {
		responseLabel.setHTML("");
	}

	@Override
	public void showBusySignal() {
		responseLabel.setHTML("Searching Scripture...");
	}

	@Override
	public void showErrorMessage(String errorMessage) {
		responseLabel.setHTML("Error: " + errorMessage);
	}

	@Override
	public void showNoResultsMessage() {
		responseLabel.setHTML("No results found");
	}

	@Override
	public void enableSubmitButton() {
		submitButton.setEnabled(true);
	}

	@Override
	public void disableSubmitButton() {
		submitButton.setEnabled(false);
	}

	@Override
	public String getQueryString() {
		return queryField.getText();
	}

	@Override
	public String getQueryType() {
		return queryTypeList.getValue(queryTypeList.getSelectedIndex());
	}

	@Override
	public void clearResults() {
		resultsTable.removeAllRows();
	}
}
