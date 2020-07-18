package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import controller.*;
import controller.Router;

public class APIServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		
		String URI = req.getRequestURI().replace("/rocp-project/api/", "");
		String[] portions = URI.split("/");
		int ID = 0;

		if (portions.length == 2) {
			try {
				ID = Integer.parseInt(portions[1]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				res.getWriter().println("The id you provided is not an integer");
				res.setStatus(400);
				return;
			}
		}

		String results = new String("");
		if (portions[0].equals("user")) {
			if (portions.length == 2) {
				results = AccountHolderAPI.detail(ID);
				if (results==null) { 
					res.getWriter().println("The id you provided was not found");
					res.setStatus(404);
					return; 
					}
			} else {
				results = AccountHolderAPI.list();
			}
		} else if (portions[0].equals("account")) {
			if (portions.length == 2) {
				results = AccountAPI.detail(ID);
				if (results==null) { 
					res.getWriter().println("The id you provided was not found");
					res.setStatus(404);
					return; 
					}
			} else {
				results = AccountAPI.list();
			}
		} else if (portions[0].equals("accountstatus")) {
			if (portions.length == 2) {
				results = AccountStatusAPI.detail(ID);
				if (results==null) { 
					res.getWriter().println("The id you provided was not found");
					res.setStatus(404);
					return; 
					}
			} else {
				results = AccountStatusAPI.list();
			}
		} else if (portions[0].equals("accounttype")) {
			if (portions.length == 2) {
				results = AccountTypeAPI.detail(ID);
				if (results==null) { 
					res.getWriter().println("The id you provided was not found");
					res.setStatus(404);
					return; 
					}
			} else {
				results = AccountTypeAPI.list();
			}
		} else if (portions[0].equals("role")) {
			if (portions.length == 2) {
				results = RoleAPI.detail(ID);
				if (results==null) { 
					res.getWriter().println("The id you provided was not found");
					res.setStatus(404);
					return; 
					}
			} else {
				results = RoleAPI.list();
			}
		} else {
			res.getWriter().println("The id you provided is not an integer");
			res.setStatus(404);
			return;
		}
		res.setStatus(200);
		PrintWriter out = res.getWriter();
		out.print(results);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
		
		System.out.println(jsonObject.get("status"));
	}
}