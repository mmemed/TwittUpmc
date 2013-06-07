package services.comment;

import java.util.ArrayList;

import org.json.JSONObject;

import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;

import bd.CommentTools;

import services.ServicesTools;

public class CommentService {


		
		public static JSONObject AddComment(String key, String text) {

			if ((key==null)||(key=="")||(text==null)||(text==""))
			{
					return ServicesTools.error("parametres inexistant",100);
			}
			try{
				if(ServicesTools.KeyValid(key)){
					return CommentTools.AddComment(key, text);
					 
				}
				else {
						return ServicesTools.error("la clé est invalide", 15);
				}
			
			}
			catch(Exception e)
				{
					return ServicesTools.error("Erreur de BD : "+e.getMessage(), 999);
				}
		
	}

		public static JSONObject ListComment(String key) {
			JSONObject js=new JSONObject();
			ArrayList<String> liste = new ArrayList<String>();
			if ((key==null)||(key==""))
			{
					return ServicesTools.error("parametre inexistant",100);
			}
			try{
				if(ServicesTools.KeyValid(key)){
					liste=CommentTools.ListComment(key);
				
					for(int i=0; i<liste.size();i++){
						
						js.put("comment",liste.get(i).toString());
						
					}
					
					return js;
				}
				else {
						return ServicesTools.error("la clé est invalide", 15);
				}
			
			}
			catch(Exception e)
				{
					return ServicesTools.error("Erreur de BD : "+e.getMessage(), 999);
				}
		
	
		}	
		

		
		public static JSONObject GetCommentFriend(String key) {
			
			JSONObject js=new JSONObject();
			ArrayList<JSONObject> liste = new ArrayList<JSONObject>();
			if ((key==null)||(key==""))
			{
					return ServicesTools.error("parametres inexistant",100);
			}
			try{
				if(ServicesTools.KeyValid(key)){
					
					
					liste=CommentTools.getCommentfriend(key);
					
					for(int i=0; i<liste.size();i++){
						System.out.println(liste.get(i).toString());
						js.put("comment"+i+"",liste.get(i).toString());
						
					}
					
					return js;
				}
				else {
						return ServicesTools.error("la clé est invalide", 15);
				}
			
			}
			catch(Exception e)
				{
					return ServicesTools.error("Erreur de BD : "+e.getMessage(), 999);
				}
		
	}

		public static JSONObject GetCommentUser() {
			JSONObject js=new JSONObject();
			ArrayList<JSONObject> liste = new ArrayList<JSONObject>();
			
			try{
				
					
					
					liste=CommentTools.getCommentUser();
					
					for(int i=0; i<liste.size();i++){
						System.out.println(liste.get(i).toString());
						js.put("comment"+i+"",liste.get(i).toString());
						
					}
					
					return js;
				
				
			
			}
			catch(Exception e)
				{
					return ServicesTools.error("Erreur de BD : "+e.getMessage(), 999);
				}
		}
		
		
}
