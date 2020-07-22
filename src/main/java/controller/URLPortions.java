package controller;

import javax.servlet.http.HttpServletRequest;

public class URLPortions {
	private String[] portions;
	private int len;
	private int ID;
	private String endpoint;
	private String filterField;
	
	public URLPortions(HttpServletRequest req) throws NumberFormatException {
		String URI = req.getRequestURI().replace("/rocp-project/api/", "");
		portions = URI.split("/");
		len = portions.length;
		endpoint = portions[0];
		if (len==2) { ID = Integer.parseInt(portions[1]); }
		if (len==3) { ID = Integer.parseInt(portions[2]); filterField = portions[1]; }
	}

	@Override
	public String toString() {
		return "URLPortions [length=" + len + ", ID=" + ID + ", endpoint=" + endpoint + ", filterField="
				+ filterField + "]";
	}

	public int getLength() {
		return len;
	}

	public int getID() {
		return ID;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public String getFilterField() {
		return filterField;
	}
}
