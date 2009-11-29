package net.todd.bible.scripturelookup.client;

import static org.mockito.Mockito.mock;

import org.junit.Test;

public class LookupPresenterTest {
	@Test
	public void test() {
		ILookupView view = mock(ILookupView.class);
		ILookupModel model = mock(ILookupModel.class);
		
		new LookupPresenter(view, model);
	}
}
