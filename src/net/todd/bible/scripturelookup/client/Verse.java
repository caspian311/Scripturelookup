package net.todd.bible.scripturelookup.client;

import com.google.gwt.json.client.JSONObject;

public class Verse {
	private final JSONObject underlyingJSObject;

	public Verse(JSONObject underlyingJSObject) {
		this.underlyingJSObject = underlyingJSObject;
	}

	public String getBook() {
		return extractStringValue("book");
	}
	
	public String getChapter() {
		return extractStringValue("chapter");
	}

	public String getVerse() {
		return extractStringValue("verse");
	}

	public String getText() {
		return extractStringValue("text");
	}

	private String extractStringValue(String key) {
		return underlyingJSObject.get(key).isString().stringValue();
	}
}
