package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsersAPI {
	
	public static String list(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("Users API");
		return "<h1>list from the users api</h1>";
	}
	
}
