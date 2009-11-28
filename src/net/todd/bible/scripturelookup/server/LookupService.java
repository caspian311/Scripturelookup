package net.todd.bible.scripturelookup.server;

import net.todd.bible.scripturelookup.client.ILookupService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LookupService extends RemoteServiceServlet implements ILookupService {
	private static final long serialVersionUID = -4750186401651003378L;

	public String lookup(String query) {
		return "Search results for: " + query;
	}
}