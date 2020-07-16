package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Router;

public class APIServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("In doGet");
		req.getRequestDispatcher(Router.process(req, res)).forward(req, res);
		System.out.println("Still in doGet");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("In doPost");
		req.getRequestDispatcher(Router.process(req, res)).forward(req, res);
	}

}