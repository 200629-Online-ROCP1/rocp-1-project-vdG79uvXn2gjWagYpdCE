package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import controller.*;
import model.*;
import utils.JWT;
import utils.Password;

public class AuthServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		String body = new String(s);
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		try {
			Object jsonObj = parser.parse(body);
			jsonObject = (JSONObject) jsonObj;
			
		} catch (ParseException e){
			System.out.println("position: " + e.getPosition());
			System.out.println(e);
			res.getWriter().println("The json provided is not parsable");
			res.setStatus(400);
			return;
		}
		
		String username = jsonObject.get("username").toString();
		String password = jsonObject.get("password").toString();
		AccountHolder user = AccountHolder.search(username);
		if (!user.getField("password").equals(Password.makeSHA256(password))) {
			res.getWriter().println("Username and password do not match");
			res.setStatus(401);
			return;
		}
		String jwt = JWT.create(username);
		JSONObject jsonjwt = new JSONObject();
		jsonjwt.put("token", jwt);
		res.getWriter().println(jsonjwt.toJSONString());
		res.setStatus(200);
	}
}