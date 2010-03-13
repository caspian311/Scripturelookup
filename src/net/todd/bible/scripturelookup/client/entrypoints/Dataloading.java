package net.todd.bible.scripturelookup.client.entrypoints;

import net.todd.bible.scripturelookup.client.model.DataDeletingModel;
import net.todd.bible.scripturelookup.client.model.DataLoadingModel;
import net.todd.bible.scripturelookup.client.model.FileProvider;
import net.todd.bible.scripturelookup.client.presenter.DataDeletingPresenter;
import net.todd.bible.scripturelookup.client.presenter.DataLoadingPresenter;
import net.todd.bible.scripturelookup.client.service.DataDeletingServiceCaller;
import net.todd.bible.scripturelookup.client.service.DataLoadingServiceCaller;
import net.todd.bible.scripturelookup.client.service.IDataDeletingService;
import net.todd.bible.scripturelookup.client.service.IDataDeletingServiceAsync;
import net.todd.bible.scripturelookup.client.service.IDataLoadingService;
import net.todd.bible.scripturelookup.client.service.IDataLoadingServiceAsync;
import net.todd.bible.scripturelookup.client.view.DataManagementView;
import net.todd.bible.scripturelookup.client.view.IDataManagementView;

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
