package net.todd.bible.scripturelookup.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class ResultsParser implements IResultsParser {
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
