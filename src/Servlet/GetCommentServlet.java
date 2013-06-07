package Servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.comment.CommentService;
import services.friend.FriendService;

public class GetCommentServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		String key= req.getParameter("key");
		
		
		
		JSONObject json = null;
		json = CommentService.GetCommentFriend(key);
		resp.getWriter().print(json.toString());
}
}
