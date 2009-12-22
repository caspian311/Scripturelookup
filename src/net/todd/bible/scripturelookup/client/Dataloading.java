package net.todd.bible.scripturelookup.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class Dataloading implements EntryPoint {
	private final IDataLoadingServiceAsync dataManagementService = GWT
			.create(IDataLoadingService.class);

	@Override
	public void onModuleLoad() {
		new DataLoadingPresenter(new DataLoadingView(), new DataLoadingModel(
				dataManagementService));
	}
}
