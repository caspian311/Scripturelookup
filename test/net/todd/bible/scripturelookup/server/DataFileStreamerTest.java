package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class DataFileStreamerTest {
	private static final String DATA_FILE = "/datafileStreamerTest.txt";
	
	private DataFileStreamer dataFileStreamer;
	private String expectedText;

	@Before
	public void setUp() throws IOException {
		dataFileStreamer = new DataFileStreamer();

		expectedText = IOUtils
				.toString(getClass().getResourceAsStream(DATA_FILE));
	}

	@Test
	public void getInputStreamsFromClasspath() throws IOException {
		InputStream streamForFile = dataFileStreamer.getStreamForFile(DATA_FILE);

		String actualText = IOUtils.toString(streamForFile);
		assertEquals(actualText, expectedText);
	}
}
