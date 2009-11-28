package net.todd.bible.scripturelookup.server;

import net.todd.bible.scripturelookup.client.ILookupService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LookupService extends RemoteServiceServlet implements ILookupService {
	private static final long serialVersionUID = -4750186401651003378L;

	public String lookup(String query) {
		StringBuffer response = new StringBuffer();

		response.append("[");
		response.append("	{");
		response.append("		\"book\"").append(" : ").append("\"John\"").append(",");
		response.append("		\"chapter\"").append(" : ").append("\"3\"").append(",");
		response.append("		\"verse\"").append(" : ").append("\"16\"").append(",");
		response.append("		\"text\"").append(" : ").append("\"For God so loved the world...\"");
		response.append("	},");
		response.append("	{");
		response.append("		\"book\"").append(" : ").append("\"Eph\"").append(",");
		response.append("		\"chapter\"").append(" : ").append("\"3\"").append(",");
		response.append("		\"verse\"").append(" : ").append("\"8\"").append(",");
		response.append("		\"text\"").append(" : ").append("\"Although I am less than the least of all God's people...\"");
		response.append("	}");
		response.append("]");

		return response.toString();
	}
}