package net.todd.bible.scripturelookup.client.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.todd.bible.scripturelookup.client.Verse;
import net.todd.bible.scripturelookup.client.parsers.IReferenceListParser;
import net.todd.bible.scripturelookup.client.parsers.ParsedResults;
import net.todd.bible.scripturelookup.client.parsers.ResultsParser;

import org.junit.Before;
import org.junit.Test;

public class ResultsParserTest {
	private ResultsParser parser;
	private IReferenceListParser referenceListParser;

	@Before
	public void setUp() {
		referenceListParser = mock(IReferenceListParser.class);
		parser = new ResultsParser(referenceListParser);
	}

	@Test
	public void returnedParsedResultsIsNotNullEvenIfAskedToParseNullText() {
		assertNotNull(parser.parse(null));
	}

	@Test
	public void returnedParsedResultsIsNotNullEvenIfAskedToParseEmptyText() {
		assertNotNull(parser.parse(""));
	}

	@Test
	public void referenceListParserGivenText() {
		String text = UUID.randomUUID().toString();

		parser.parse(text);

		verify(referenceListParser).parse(text);
	}
	
	@Test
	public void referenceListInParsedResultsAreFromReferenceListParser() {
		List<Verse> referenceList = Arrays.asList();
		doReturn(referenceList).when(referenceListParser).parse(anyString());
		ParsedResults parsedResults = parser.parse(anyString());

		assertSame(referenceList, parsedResults.getReferenceList());
	}
	
	@Test
	public void metaDataShouldContainSizeOfList() {
		Verse verse1 = mock(Verse.class);
		Verse verse2 = mock(Verse.class);
		Verse verse3 = mock(Verse.class);
		Verse verse4 = mock(Verse.class);
		List<Verse> referenceList = Arrays.asList(verse1, verse2, verse3, verse4);
		doReturn(referenceList).when(referenceListParser).parse(anyString());
		ParsedResults parsedResults = parser.parse(anyString());

		assertEquals("Total results: 4", parsedResults.getMetaData().toString());
	}
}
