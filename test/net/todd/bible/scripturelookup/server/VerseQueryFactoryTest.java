package net.todd.bible.scripturelookup.server;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.todd.bible.scripturelookup.server.data.IVerseQueryFactory;
import net.todd.bible.scripturelookup.server.data.Verse;
import net.todd.bible.scripturelookup.server.data.VerseQueryFactory;

import org.junit.Before;
import org.junit.Test;

public class VerseQueryFactoryTest {
	private IVerseQueryFactory queryFactory;
	private String book;
	private List<Integer> multipleChapters;
	private List<Integer> multipleVerses;
	private List<Integer> singleChapter;
	private List<Integer> singleVerse;
	private ArrayList<Integer> emptyChapters;
	private ArrayList<Integer> emptyVerses;

	@Before
	public void setUp() {
		queryFactory = new VerseQueryFactory();

		book = UUID.randomUUID().toString();
		emptyChapters = new ArrayList<Integer>();
		singleChapter = Arrays.asList(1);
		multipleChapters = Arrays.asList(1, 2, 3);
		emptyVerses = new ArrayList<Integer>();
		singleVerse = Arrays.asList(4);
		multipleVerses = Arrays.asList(4, 5, 6);
	}

	@Test
	public void bookWithNoChaptersOrVerses() {
		String actualQuery = queryFactory.createQuery(book, emptyChapters, emptyVerses);
		String expectedQuery = "SELECT FROM " + Verse.class.getName() + " WHERE book == '" + book
				+ "'";

		assertEquals(expectedQuery, actualQuery);
	}

	@Test
	public void bookWithOneChapterButNoVerses() {
		String actualQuery = queryFactory.createQuery(book, singleChapter, emptyVerses);
		String exepctedQuery = "SELECT FROM " + Verse.class.getName() + " WHERE book == '" + book
				+ "' && (chapter == 1)";

		assertEquals(exepctedQuery, actualQuery);
	}

	@Test
	public void bookWithOneChapterAndOneVerse() {
		String actualQuery = queryFactory.createQuery(book, singleChapter, singleVerse);
		String exepctedQuery = "SELECT FROM " + Verse.class.getName() + " WHERE book == '" + book
				+ "' && (chapter == 1) && (verse == 4)";

		assertEquals(exepctedQuery, actualQuery);
	}
	
	@Test
	public void bookWithOneChapterAndManyVerses() {
		String actualQuery = queryFactory.createQuery(book, singleChapter, multipleVerses);
		String exepctedQuery = "SELECT FROM " + Verse.class.getName() + " WHERE book == '" + book
				+ "' && (chapter == 1) && (verse == 4 || verse == 5 || verse == 6)";

		assertEquals(exepctedQuery, actualQuery);
	}
	
	@Test
	public void bookWithManyChaptersButNoVerses() {
		String actualQuery = queryFactory.createQuery(book, multipleChapters, emptyVerses);
		String exepctedQuery = "SELECT FROM " + Verse.class.getName() + " WHERE book == '" + book
				+ "' && (chapter == 1 || chapter == 2 || chapter == 3)";

		assertEquals(exepctedQuery, actualQuery);
	}
}
