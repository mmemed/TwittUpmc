package services.test;

import org.json.JSONObject;

import services.comment.CommentService;
import services.friend.FriendService;

public class SearchFriendTest {
	
	public static void main(String args[]) throws Exception{
		
		JSONObject js=FriendService.SearchFriends("387E5787E41430DDA9C553C640B1131D","ah");
		
		System.out.println(js.toString());
		
		
	}

}
