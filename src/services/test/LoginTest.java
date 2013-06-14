package services.test;

import org.json.JSONException;
import org.json.JSONObject;

import services.user.UserService;

public class LoginTest {
	
	public static void main(String args[]) throws Exception{
		
		JSONObject test = UserService.LoginUser(null,"5645512");
		System.out.println(test.toString());
		
		
		
	}

}


