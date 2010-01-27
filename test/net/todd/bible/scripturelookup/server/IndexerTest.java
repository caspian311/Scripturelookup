package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IndexerTest {
	private ByteArrayOutputStream err;
	private ByteArrayOutputStream out;
	
	private IIndexBuilder indexBuilder;
	
	private String goodDirectory;

	@Before
	public void setUp() throws IOException {
		err = new ByteArrayOutputStream();
		out = new ByteArrayOutputStream();
		
		indexBuilder = mock(IIndexBuilder.class);
		
		File tempLocation = File.createTempFile(getClass().getName(), "");
		goodDirectory = tempLocation.getAbsolutePath();
		FileUtils.forceDelete(new File(goodDirectory));
		new File(goodDirectory).mkdir();
	}

	@After
	public void tearDown() throws IOException {
		FileUtils.forceDelete(new File(goodDirectory));
	}

	private String getOutput() {
		return streamToString(out);
	}

	private String getError() {
		return streamToString(err);
	}

	private String streamToString(ByteArrayOutputStream stream) {
		String text = new String(stream.toByteArray());
		return trimTrailingWhitespace(text);
	}

	private String getUsageMessage() {
		return trimTrailingWhitespace(Indexer.USAGE_ERROR_MESSAGE);
	}

	private String getSuccessMessage() {
		return trimTrailingWhitespace(Indexer.SUCCESS_MESSAGE);
	}
	
	private String trimTrailingWhitespace(String text) {
		return text.replaceAll("\\s+$", "");
	}
	
	@Test
	public void ifGivenNoArgumentShowUsageToErrorStream() {
		String[] args = new String[0];

		new Indexer(out, err, indexBuilder).doit(args);
		
		assertEquals("", getOutput());
		assertEquals(getUsageMessage(), getError());
	}
	
	@Test
	public void ifTooManyArgumentShowUsageToErrorStream() {
		String[] args = { "", "" };

		new Indexer(out, err, indexBuilder).doit(args);

		assertEquals("", getOutput());
		assertEquals(getUsageMessage(), getError());
	}

	@Test
	public void ifGivenOneEmptyArgumentShowUsageToErrorStream() {
		String[] args = { "" };

		new Indexer(out, err, indexBuilder).doit(args);

		assertEquals("", getOutput());
		assertEquals(getUsageMessage(), getError());
	}
	
	@Test
	public void ifABadPathArgumentShowUsageToErrorStream() {
		String[] args = { UUID.randomUUID().toString() };

		new Indexer(out, err, indexBuilder).doit(args);

		assertEquals("", getOutput());
		assertEquals(getUsageMessage(), getError());
	}

	@Test
	public void ifAGoodPathArgumentGivenAndErrorsOccursShowErrorMessgaeToErrorStream() {
		String errorMessage = UUID.randomUUID().toString();
		String[] args = { goodDirectory };

		doThrow(new RuntimeException(errorMessage)).when(indexBuilder).createIndex(anyString());

		new Indexer(out, err, indexBuilder).doit(args);

		assertEquals("", getOutput());
		assertEquals(Indexer.ERROR_MESSAGE_PREFIX + errorMessage, getError());
	}

	@Test
	public void pathArgumentIsGivenToTheIndexBuilder() {
		String[] args = { goodDirectory };

		new Indexer(out, err, indexBuilder).doit(args);

		verify(indexBuilder).createIndex(goodDirectory);
	}

	@Test
	public void ifAGoodPathArgumentGivenAndNoErrorsOccurShowSuccessToOutputStream() {
		String[] args = { goodDirectory };

		new Indexer(out, err, indexBuilder).doit(args);

		assertEquals(getSuccessMessage(), getOutput());
		assertEquals("", getError());
	}
}
