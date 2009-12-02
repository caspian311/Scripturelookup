package net.todd.bible.scripturelookup.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class DataManagementPresenterTest {
	@Test
	public void tellModelToReloadDataWhenButtonPressedOnView() {
		IDataManagementView view = mock(IDataManagementView.class);
		IDataManagementModel model = mock(IDataManagementModel.class);

		new DataManagementPresenter(view, model);

		ArgumentCaptor<IListener> captor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addReloadButtonListener(captor.capture());

		captor.getValue().handleEvent();

		verify(model).reloadData();
	}
}