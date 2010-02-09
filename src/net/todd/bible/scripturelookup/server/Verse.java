package net.todd.bible.scripturelookup.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Verse {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String book;
	
	@Persistent
	private Integer chapter;
	
	@Persistent
	private Integer verse;
	
	@Persistent
	private Text text;

	public Verse(String book, Integer chapter, Integer verse, String text) {
		this.book = book;
		this.chapter = chapter;
		this.verse = verse;
		this.text = new Text(text);
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

	public Integer getChapter() {
		return chapter;
	}

	public Integer getVerse() {
		return verse;
	}

	public String getText() {
		return text.getValue();
	}

	public void setBook(String book) {
		this.book = book;
	}

	public void setChapter(Integer chapter) {
		this.chapter = chapter;
	}

	public void setVerse(Integer verse) {
		this.verse = verse;
	}

	public void setText(String text) {
		this.text = new Text(text);
	}
	
	@Override
	public String toString() {
		return getReference() + " - " + text.getValue();
	}

	public String getReference() {
		return book + " " + chapter + ":" + verse;
	}
}
