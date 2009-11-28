package net.todd.bible.scripturelookup.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Verse {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String book;
	
	@Persistent
	private String chapter;
	
	@Persistent
	private String verse;
	
	@Persistent
	private String text;

	public Verse(String book, String chapter, String verse, String text) {
		this.book = book;
		this.chapter = chapter;
		this.verse = verse;
		this.text = text;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
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

	public void setBook(String book) {
		this.book = book;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public void setVerse(String verse) {
		this.verse = verse;
	}

	public void setText(String text) {
		this.text = text;
	}
}
