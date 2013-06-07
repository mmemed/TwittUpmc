package services.test;

import org.json.JSONException;
import org.json.JSONObject;

import services.comment.CommentService;
import services.user.UserService;

public class listCommentTest {
	
	public static void main(String args[]) throws Exception{
		
		JSONObject js=CommentService.ListComment("387E5787E41430DDA9C553C640B1131D");
		
		System.out.println(js.toString());
		
		
	}

}
