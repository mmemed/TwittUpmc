package services.friend;

import org.json.JSONObject;

import services.ServicesTools;
import bd.CommentTools;
import bd.FriendTools;
import bd.UsersTools;

public class FriendService {

	public static JSONObject AddFriend(String key, String logFriends) {

		if ((key==null)||(key=="")||(logFriends==null)||(logFriends==""))
		{
				return ServicesTools.error("parametres inexistant",100);
		}
		try
		{
			if(ServicesTools.KeyValid(key)){
				String idFriends=UsersTools.getid(logFriends);
				if(FriendTools.FriendExist(key,idFriends)){
					return ServicesTools.error("amis déja ajouté", 11);
				}
				else{
					FriendTools.AddFriend(key,idFriends);
					return ServicesTools.ok();
				}
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
	
	public static JSONObject RemoveFriend(String key, String logFriends) {

		if ((key==null)||(key=="")||(logFriends==null)||(logFriends==""))
		{
				return ServicesTools.error("parametres inexistant",100);
		}
		try
		{		
			if(ServicesTools.KeyValid(key)){
				String idFriends=UsersTools.getid(logFriends);
				if(!FriendTools.FriendExist(key,idFriends)){
					return ServicesTools.error("cet amis n'est pas dans votre liste d'amis", 12);
		}
				else{
			FriendTools.RemoveFriend(key, idFriends);
				return ServicesTools.ok();
				}
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
	
	public static JSONObject ListFriends(String key){
		
		if ((key==null)||(key==""))
		{
				return ServicesTools.error("parametres inexistant",100);
		}
		
		try
		{
			if(ServicesTools.KeyValid(key))
			{
				return FriendTools.listfriend(key);
			}
			else return ServicesTools.error("la clé est invalide", 15);
		}
		catch(Exception e)
		{
			return ServicesTools.error("Erreur de BD : "+e.getMessage(), 999);
		}
	}
	
	
public static JSONObject SearchFriends(String key,String Queryfriend){
		
		if ((key==null)||(key=="") ||(Queryfriend==null)||(Queryfriend==""))
		{
				return ServicesTools.error("parametres inexistant",100);
		}
		
		try
		{
			if(ServicesTools.KeyValid(key))
			{
				return FriendTools.searchFriend(Queryfriend);
			}
			else return ServicesTools.error("la clé est invalide", 15);
		}
		catch(Exception e)
		{
			return ServicesTools.error("Erreur de BD : "+e.getMessage(), 999);
		}
	}
}