package net.todd.bible.scripturelookup.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class Scripturelookup implements EntryPoint {
	private final ILookupServiceAsync lookupService = GWT.create(ILookupService.class);
	
	public void onModuleLoad() {
		new LookupPresenter(new LookupView(), new LookupModel(new LookupServiceCaller(lookupService),
				new ResultsParser()));
	}
}
