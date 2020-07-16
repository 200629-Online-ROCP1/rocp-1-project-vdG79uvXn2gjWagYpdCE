package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Router {
	
	public static String process(HttpServletRequest req, HttpServletResponse res) {
		System.out.println(req.getRequestURI().toString());

		if (req.getRequestURI().toString().equals("/rocp-project/api/user")) { return UserAPI.list(req, res); }
		if (req.getRequestURI().toString().equals("/rocp-project/api/account")) { return AccountAPI.list(req, res); }
		if (req.getRequestURI().toString().equals("/rocp-project/api/accountstatus")) { return AccountStatusAPI.list(req, res); }
		if (req.getRequestURI().toString().equals("/rocp-project/api/accounttype")) { return AccountTypeAPI.list(req, res); }
		if (req.getRequestURI().toString().equals("/rocp-project/api/role")) { return RoleAPI.list(req, res); }
		
		return "/index.html"; 
	}

}
