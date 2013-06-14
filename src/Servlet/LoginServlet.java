package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.user.UserService;

public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException 
	{
		
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		String login= req.getParameter("login");
		String password= req.getParameter("password");
		
		JSONObject json = null;
		json = UserService.LoginUser(login, password);
		resp.getWriter().print(json.toString());
	}
}
