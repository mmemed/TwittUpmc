package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import bd.BDException;

import services.user.UserService;

public class CreateUserServlet extends HttpServlet {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		String login= req.getParameter("login");
		String password= req.getParameter("password");
		String nom= req.getParameter("nom");
		String prenom= req.getParameter("prenom");
		String mail= req.getParameter("mail");
		
		
		JSONObject json = null;
		json = UserService.createUsers(login, password, nom, prenom, mail);
		resp.getWriter().print(json.toString());
}
}
