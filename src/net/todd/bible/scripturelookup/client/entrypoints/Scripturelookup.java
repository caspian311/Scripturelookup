package net.todd.bible.scripturelookup.client.entrypoints;

import net.todd.bible.scripturelookup.client.model.LookupModel;
import net.todd.bible.scripturelookup.client.parsers.ReferenceListParser;
import net.todd.bible.scripturelookup.client.parsers.ResultsParser;
import net.todd.bible.scripturelookup.client.presenter.LookupPresenter;
import net.todd.bible.scripturelookup.client.service.ILookupService;
import net.todd.bible.scripturelookup.client.service.ILookupServiceAsync;
import net.todd.bible.scripturelookup.client.service.LookupServiceCaller;
import net.todd.bible.scripturelookup.client.view.LookupView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class Scripturelookup implements EntryPoint {
	private final ILookupServiceAsync lookupService = GWT.create(ILookupService.class);
	
	public void onModuleLoad() {
		new LookupPresenter(new LookupView(), new LookupModel(new LookupServiceCaller(lookupService),
				new ResultsParser(new ReferenceListParser())));
	}
}
