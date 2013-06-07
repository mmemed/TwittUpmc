package services.test;

import org.json.JSONObject;

import bd.UsersTools;

import services.comment.CommentService;
import services.user.UserService;

public class CommentFriendTest {
public static void main(String args[]) throws Exception{
		
		JSONObject js=UserService.LoginUser("ahmed","123");
		
		
		System.out.println(js.toString());
		
		
	}

}


