package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserAPI {
	
	public static String list(HttpServletRequest req, HttpServletResponse res) {
		return "<h1>list from the users api</h1>";
	}
	
}
