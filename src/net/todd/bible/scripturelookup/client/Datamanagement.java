package net.todd.bible.scripturelookup.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class Datamanagement implements EntryPoint {
	private final IDataManagementServiceAsync dataManagementService = GWT
			.create(IDataManagementService.class);

	@Override
	public void onModuleLoad() {
		new DataManagementPresenter(new DataManagementView(), new DataManagementModel(
				dataManagementService));
	}
}
