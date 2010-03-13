package net.todd.bible.scripturelookup.client.parsers;

import java.util.ArrayList;
import java.util.List;

import net.todd.bible.scripturelookup.client.Verse;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class ReferenceListParser implements IReferenceListParser {
	@Override
	public List<Verse> parse(String response) {
		List<Verse> searchResults = new ArrayList<Verse>();
		JSONValue parsedValue = JSONParser.parse(response);

		if (parsedValue.isArray() != null) {
			JSONArray responseArray = parsedValue.isArray();

			for (int i = 0; i < responseArray.size(); i++) {
				JSONValue value = responseArray.get(i);
				searchResults.add(new Verse(value.isObject()));
			}
		}
		return searchResults;
	}
}
