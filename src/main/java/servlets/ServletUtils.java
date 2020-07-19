package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import model.AccountHolder;
import utils.JWT;

public class ServletUtils {
	public static HttpServletResponse sendMessage(HttpServletResponse res, int status, String message) throws IOException {
		JSONObject obj = new JSONObject();
		obj.put("message", message);
		res.getWriter().println(obj.toJSONString());
		res.setStatus(status);
		return res;
	}

}