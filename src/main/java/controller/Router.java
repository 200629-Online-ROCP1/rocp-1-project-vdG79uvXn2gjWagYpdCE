package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Router {
	
	public static String process(HttpServletRequest req, HttpServletResponse res) {
		System.out.println(req.getRequestURI().toString());

		if (req.getRequestURI().toString().equals("/rocp-project/api/users")) {
			return UsersAPI.list(req, res);
		}		
		return "/index.html"; 
	}

}
