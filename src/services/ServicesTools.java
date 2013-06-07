package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.ExceptionList;

import bd.BDException;
import bd.FriendTools;
import bd.UsersTools;

public class ServicesTools {

	private static char hexTab[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	private static int lg = Integer.MAX_VALUE;
	
	public static JSONObject error(String message, int error_code)
	{
		try
		{
			 JSONObject out = new JSONObject();
			 out.put("error_code", error_code);
			 return out;
		}
		catch(Exception e)
		{
			return(null);
		}
	}

	public static JSONObject ok() {
		JSONObject out = new JSONObject();
		return out;
	}
	
	public static String key32(){
		String str = "";
		for (int i=0; i<32; i++)
		{
			int r = (int)(Math.random() * lg);
			str += hexTab[r%15];
		}		
		return str;
	}
	
	public static boolean KeyValid(String Key) throws Exception{
		boolean val=false;
		
		String loginUser="";
		int iduser=0;
		try {
			Connection c = UsersTools.getMySQLConnection();
			Statement s = c.createStatement();
			ResultSet res = s.executeQuery("SELECT login FROM `session` WHERE `key`='"+Key+"' AND `expire`=1;");
			if (res.next()){
				res.close();
				s.close();
				val=true;
			}
		else {
			res.close();
			s.close();
			val=false;
		}
		return val;
		
	}catch(Exception e){
		throw new Exception("erreur à Keyvalid");
	}
   }
}
