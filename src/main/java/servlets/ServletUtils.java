package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ServletUtils {
	
	public static HttpServletResponse sendMessage(HttpServletResponse res, int status, String message) throws IOException {
		JSONObject obj = new JSONObject();
		obj.put("message", message);
		res.getWriter().println(obj.toJSONString());
		res.setStatus(status);
		return res;
	}
	
	public static HttpServletResponse APISetup(HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
		return res;
	}

	public static JSONObject bodyAsJSON(HttpServletRequest req, HttpServletResponse res) throws IOException, org.json.simple.parser.ParseException  {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		String body = new String(s);
		Object jsonObj = parser.parse(body);
		jsonObject = (JSONObject) jsonObj;
		return jsonObject;
	}
}