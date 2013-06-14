	package services.user;
	
	import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
	

	import bd.BDException;

import bd.UsersTools;
	
import services.ServicesTools;

	
	public class UserService {
		
		public static JSONObject createUsers(String login, String password, String nom, String prenom, String mail) 
		{
			
			
			if ((login==null)||(login=="")||(password==null)||(password=="")||
					(nom=="")||(nom==null)||(prenom=="")||(prenom==null)||(mail=="")||(mail==null))
			{
					return ServicesTools.error("parametres inexistant",100);
			}
			try
			{
				if(UsersTools.UserExist(login)){
						return ServicesTools.error("login existant", 1);
				}
				else{
						UsersTools.createUser(login,password,nom,prenom,mail);
						return ServicesTools.ok();
				}
			}
			catch(Exception e)
			{
				return ServicesTools.error("Erreur de BD : "+e.getMessage(), 999);
			}
		}
		
		public static JSONObject LoginUser(String login, String password) 
		{
			JSONObject js=new JSONObject();
			try {
				
			
			if((login==null)|| login.equals("")||(password==null) || password.equals("")){
				return ServicesTools.error("parametres inexistant",100);
			}
			
			
				else
				{
					if (!UsersTools.UserExist(login))
					{
						js = ServicesTools.error("Login inexistant. ",2);
					}
					else {
							if (UsersTools.userConnect(login, password)){
							js=UsersTools.Userkey(login);
							
		  				}  else{
							js = ServicesTools.error("Mauvais mot de passe. ",3);}
				}
				
				
			}
		} catch(Exception e)
		{
			return ServicesTools.error("Erreur de BD : "+e.getMessage(), 999);
		}
			return js;
		}
		
		
		public static JSONObject logoutUser(String key) {
			JSONObject js = new JSONObject();
			try {
				
			
			if((key==null) || key.equals("")){
				return ServicesTools.error("parametres inexistant",100);
			}
			else
			{
				js=UsersTools.logoutUser(key);
			}
		}  catch(Exception e)
		{
			return ServicesTools.error("Erreur de BD : "+e.getMessage(), 999);
		}
		return js;
	}

		public static JSONObject ListUser(String key) {
			JSONObject js = new JSONObject();
			try {
				
			
			if((key==null) || key.equals("")){
				return ServicesTools.error("parametres inexistant",100);
			}
			else
			{
				js=UsersTools.listUser();
			}
		}  catch(Exception e)
		{
			return ServicesTools.error("Erreur de BD : "+e.getMessage(), 999);
		}
		return js;
	}
}
			
		
		
		
	
	  
	
		
		
