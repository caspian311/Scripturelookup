package net.todd.bible.scripturelookup.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class Dataloading implements EntryPoint {
	private final IDataDeletingServiceAsync dataDeletingService = GWT
			.create(IDataDeletingService.class);
	private final IDataLoadingServiceAsync dataLoadingService = GWT
			.create(IDataLoadingService.class);

	@Override
	public void onModuleLoad() {
		IDataManagementView dataManagementView = new DataManagementView();
		new DataLoadingPresenter(dataManagementView, new DataLoadingModel(
				new DataLoadingServiceCaller(dataLoadingService), new FileProvider()));
		new DataDeletingPresenter(dataManagementView, new DataDeletingModel(
				new DataDeletingServiceCaller(dataDeletingService)));
	}
}
