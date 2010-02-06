package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class JSONWriterTest {
	private IJSONWriter jsonWriter;
	private List<Verse> searchResults;

	@Before
	public void setUp() {
		searchResults = new ArrayList<Verse>();
		jsonWriter = new JSONWriter();
	}

	@Test
	public void writeOutAnEmptyListOfVerses() {
		String expectedResults = "[]";
		
		String actualResults = jsonWriter.writeOut(searchResults);

		assertEquals(expectedResults, actualResults);
	}
	
	@Test
	public void writeOutAListOfOneVerse() {
		searchResults.add(new Verse("john", "3", "16", "For God so loved the world..."));
		String expectedResults = "[{\"book\":\"john\",\"chapter\":\"3\",\"verse\":\"16\",\"text\":\"For God so loved the world...\"}]";

		String actualResults = jsonWriter.writeOut(searchResults);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void writeOutAListOfTwoVerses() {
		searchResults.add(new Verse("john", "3", "16", "For God so loved the world..."));
		searchResults.add(new Verse("gen", "1", "1", "In the beginning, God..."));
		String expectedResults = "[{\"book\":\"john\",\"chapter\":\"3\",\"verse\":\"16\",\"text\":\"For God so loved the world...\"},"
				+ "{\"book\":\"gen\",\"chapter\":\"1\",\"verse\":\"1\",\"text\":\"In the beginning, God...\"}]";

		String actualResults = jsonWriter.writeOut(searchResults);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void writeOutAListOfThreeVerses() {
		searchResults.add(new Verse("john", "3", "16", "For God so loved the world..."));
		searchResults.add(new Verse("gen", "1", "1", "In the beginning, God..."));
		searchResults
				.add(new Verse("rev", "21", "1", "Then I saw a new heaven and a new earth..."));
		
		String expectedResults = "[{\"book\":\"john\",\"chapter\":\"3\",\"verse\":\"16\",\"text\":\"For God so loved the world...\"},"
				+ "{\"book\":\"gen\",\"chapter\":\"1\",\"verse\":\"1\",\"text\":\"In the beginning, God...\"},"
				+ "{\"book\":\"rev\",\"chapter\":\"21\",\"verse\":\"1\",\"text\":\"Then I saw a new heaven and a new earth...\"}]";

		String actualResults = jsonWriter.writeOut(searchResults);

		assertEquals(expectedResults, actualResults);
	}
}
