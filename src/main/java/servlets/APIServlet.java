package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AccountTypeAPI;
import controller.Router;

public class APIServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		res.setStatus(404);
		
		String URI = req.getRequestURI().replace("/rocp-project/api/", "");
		String[] portions = URI.split("/");
		
		System.out.println(req.getRequestURI().toString());
		System.out.println(Arrays.toString(portions));
		System.out.println(portions[0]);
		
		String results = new String("");
//		if (req.getRequestURI().toString().equals("/rocp-project/api/user")) { return UserAPI.list(req, res); }
//		if (req.getRequestURI().toString().equals("/rocp-project/api/account")) { return AccountAPI.list(req, res); }
//		if (portions[0]=="/rocp-project/api/accountstatus")) { return AccountStatusAPI.list(req, res); }
		if (portions[0].equals("accounttype")) { 
			results = AccountTypeAPI.list(); 
			System.out.println(results);
			}
//		if (req.getRequestURI().toString().equals("/rocp-project/api/role")) { return RoleAPI.list(req, res); }
		System.out.println(results);
		res.setStatus(200);
		PrintWriter out = res.getWriter();
		out.print(results);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}