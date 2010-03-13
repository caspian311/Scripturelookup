package net.todd.bible.scripturelookup.client.model;

import java.util.List;

import net.todd.bible.scripturelookup.client.IListener;
import net.todd.bible.scripturelookup.client.ListenerManager;
import net.todd.bible.scripturelookup.client.service.IDataLoadingServiceCaller;

public class DataLoadingModel implements IDataLoadingModel {
	private final ListenerManager failureListenerManager = new ListenerManager();
	private final ListenerManager progressListenerManager = new ListenerManager();
	private final ListenerManager successListenerManager = new ListenerManager();

	private final IDataLoadingServiceCaller dataLoadingServiceCaller;
	private final List<String> filesToLoad;

	protected String errorMessage;
	private int fileIndex;
	private double percentComplete;

	public DataLoadingModel(final IDataLoadingServiceCaller dataLoadingServiceCaller,
			IFileProvider fileProvider) {
		this.dataLoadingServiceCaller = dataLoadingServiceCaller;
		filesToLoad = fileProvider.filesToLoad();

		dataLoadingServiceCaller.addFailureListener(new IListener() {
			@Override
			public void handleEvent() {
				Throwable exception = dataLoadingServiceCaller.getException();
				if (exception != null) {
					errorMessage = exception.getMessage();
				}
				failureListenerManager.notifyListeners();
			}
		});

		dataLoadingServiceCaller.addSuccessListener(new IListener() {
			@Override
			public void handleEvent() {
				fileIndex++;
				updatePrecentComplete(fileIndex, filesToLoad.size());
				progressListenerManager.notifyListeners();
				if (filesToLoad.size() > fileIndex) {
					dataLoadingServiceCaller.callService(filesToLoad.get(fileIndex));
				} else {
					successListenerManager.notifyListeners();
				}
			}
		});
	}
	
	private void updatePrecentComplete(double currentFile, double totalFiles) {
		percentComplete = currentFile / totalFiles;
	}
	
	@Override
	public void reloadData() {
		fileIndex = 0; // need test for this...
		dataLoadingServiceCaller.callService(filesToLoad.get(fileIndex));
	}

	@Override
	public void addDataReloadedListener(IListener listener) {
		successListenerManager.addListener(listener);
	}

	@Override
	public void addFailureListener(IListener listener) {
		failureListenerManager.addListener(listener);
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void addProgressListener(IListener progressListener) {
		progressListenerManager.addListener(progressListener);
	}

	@Override
	public double getPercentComplete() {
		return percentComplete;
	}
}
