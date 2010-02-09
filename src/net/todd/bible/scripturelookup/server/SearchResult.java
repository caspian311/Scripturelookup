package net.todd.bible.scripturelookup.server;

public class SearchResult implements Comparable<SearchResult> {
	private float score;
	private String book;
	private Integer chapter;
	private Integer verse;
	private String text;

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public Integer getChapter() {
		return chapter;
	}

	public void setChapter(Integer chapter) {
		this.chapter = chapter;
	}

	public Integer getVerse() {
		return verse;
	}

	public void setVerse(Integer verse) {
		this.verse = verse;
	}

	@Override
	public int compareTo(SearchResult that) {
		return new Float(this.score).compareTo(new Float(that.score));
	}

}
