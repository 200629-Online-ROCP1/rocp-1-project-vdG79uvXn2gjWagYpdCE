package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.*;
import controller.Router;

public class APIServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		res.setStatus(404);
		
		String URI = req.getRequestURI().replace("/rocp-project/api/", "");
		String[] portions = URI.split("/");
		int ID=0;
		
		if (portions.length == 2) {
			try {
				ID = Integer.parseInt(portions[1]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				res.getWriter().println("The id you provided is not an integer");
				res.setStatus(400);
			}
		}
		
		String results = new String("");
//		if (req.getRequestURI().toString().equals("/rocp-project/api/user")) { return UserAPI.list(req, res); }
//		if (req.getRequestURI().toString().equals("/rocp-project/api/account")) { return AccountAPI.list(req, res); }
		if (portions[0].equals("accountstatus")) {  
			if (portions.length == 2) {
				results = AccountStatusAPI.detail(ID);
			} else {
				results = AccountStatusAPI.list(); 
			}
		} else if (portions[0].equals("accounttype")) { 
			if (portions.length == 2) {
				results = AccountTypeAPI.detail(ID);
			} else {
				results = AccountTypeAPI.list(); 
			}
		} else if (portions[0].equals("role")) { 
			if (portions.length == 2) {
				results = RoleAPI.detail(ID);
			} else {
				results = RoleAPI.list(); 
			}
		}
		res.setStatus(200);
		PrintWriter out = res.getWriter();
		out.print(results);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}