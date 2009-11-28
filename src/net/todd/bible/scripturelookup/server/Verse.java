package net.todd.bible.scripturelookup.server;

public class Verse {
	private final String book;
	private final String chapter;
	private final String verse;
	private final String text;

	public Verse(String book, String chapter, String verse, String text) {
		this.book = book;
		this.chapter = chapter;
		this.verse = verse;
		this.text = text;
	}

	public String getBook() {
		return book;
	}

	public String getChapter() {
		return chapter;
	}

	public String getVerse() {
		return verse;
	}

	public String getText() {
		return text;
	}
}
