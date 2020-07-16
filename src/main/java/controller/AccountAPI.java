package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountAPI {
	
	public static String list(HttpServletRequest req, HttpServletResponse res) {
		return "<h1>list from the accounts api</h1>";
	}
}
