package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import model.AccountHolder;
import servlets.*;
import utils.JWT;

public class Authorization {
	private static String requestOwner;
	private static String requestOwnerRole;
	private static int requestOwnerID;

	public static boolean processJWT(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String auth = req.getHeader("authorization");
		if (auth==null) { 
			res = ServletUtils.sendMessage(res, 401, "Authorization required"); 
			return false; 
		} else {
			String token = auth.replace("Bearer ", "");
			try {
				Claims claims = JWT.decode(token);
				requestOwner = claims.get("username").toString();
				AccountHolder obj = AccountHolder.search(requestOwner);
				requestOwnerRole = obj.getField("role");
				requestOwnerID = obj.getID(); 
			} catch (MalformedJwtException e) {
				res = ServletUtils.sendMessage(res, 401, "Malformed Token");
				return false;
			}
		}
		return true;
	}

	private static int getRequestOwnerID() {
		return requestOwnerID;
	}

	private static void setRequestOwnerID(int requestOwnerID) {
		Authorization.requestOwnerID = requestOwnerID;
	}

	public static String getRequestOwner() {
		return requestOwner;
	}

	public static void setRequestOwner(String requestOwner) {
		Authorization.requestOwner = requestOwner;
	}

	public static String getRequestOwnerRole() {
		return requestOwnerRole;
	}

	public static void setRequestOwnerRole(String requestOwnerRole) {
		Authorization.requestOwnerRole = requestOwnerRole;
	}
}
