package net.todd.bible.scripturelookup.server;

public class SearchResult implements Comparable<SearchResult> {
	private float score;
	private String reference;
	private String text;

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int compareTo(SearchResult that) {
		return new Float(this.score).compareTo(new Float(that.score));
	}

}
