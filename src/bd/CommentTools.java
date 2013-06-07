package bd;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.json.JSONObject;

import services.Dates;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class CommentTools {

public static DataBase database;
	
	public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName( "com.mysql.jdbc.Driver" );
        if (DBStatic.mysql_pooling==false)
        {
            return( DriverManager.getConnection("jdbc:mysql://"+DBStatic.mysql_host
                    +"/"
                    +DBStatic.mysql_db, DBStatic.mysql_username,DBStatic.mysql_password));
        }
        else
        {
            DataBase database=new DataBase("jdbc/db");
            return(database.getConnection());
        }
    }

	
	public static JSONObject AddComment(String key, String text) throws Exception {
		
		String loginUser=null;
		int iduser=0;
		try {
			Connection c = FriendTools.getMySQLConnection();
			Statement req1 = c.createStatement();
			ResultSet rs1 = req1.executeQuery("SELECT login FROM `session` WHERE `key`='"+key+"' AND `expire`=1;");
			
			while(rs1.next()){
				
				loginUser = rs1.getString(1);
			}
			req1.close();
			Statement req2 = c.createStatement();
			ResultSet rs2 = req2.executeQuery("SELECT id FROM `user` WHERE `login`='"+loginUser+"';");
			while(rs2.next()){
				 iduser = rs2.getInt(1);
			}
			req2.close();
				
		JSONObject js=new JSONObject();
	Mongo m = new Mongo("li328.lip6.fr",27130);
	DB db = m.getDB("li328");
	DBCollection coll =db.getCollection("elsayed");  
	BasicDBObject doc =new BasicDBObject();
	
	
	doc.put("id_author", iduser);
	doc.put("login_user",loginUser);
	doc.put("text", text);
	js.put("login_user",loginUser);
	js.put("text", text);
	Dates dateactu = new Dates();
	 
	doc.put("Date", dateactu.date());
	js.put("date",dateactu.date());
	coll.insert(doc);
	return js;
	
}catch(Exception e){
	throw new Exception("erreur de bd AddComment" +e.getMessage());
}
	}


	public static ArrayList<String> ListComment(String key) throws Exception {
		String loginUser="";
		
		JSONObject js=new JSONObject();
		BasicDBObject query = new BasicDBObject ();
		ArrayList<String> liste = new ArrayList <String>();
		try {
			Connection c = FriendTools.getMySQLConnection();
			loginUser=FriendTools.loginKey(key);
			c.close();
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db = m.getDB("li328");
			DBCollection coll =db.getCollection("elsayed"); 
			
		 
			 query.put("login_user",loginUser);
			 
			 DBCursor cursor = coll.find(query);
			 
			 while (cursor.hasNext()){
				 DBObject o=cursor.next();
				 liste.add(o.get("text").toString() +", date: "+ o.get("Date"));
				 }
			 
			
			 return liste;
		}catch(Exception e){
			throw new Exception("erreur de bd ListComment" +e.getMessage());
		}
		}


	public static ArrayList<JSONObject> getCommentfriend(String Key) throws Exception {
		
		JSONObject jsfriends=FriendTools.listfriend(Key);
		BasicDBObject query = new BasicDBObject ();
		BasicDBObject query2 = new BasicDBObject ();
		
		 
		 
		ArrayList<JSONObject> liste = new ArrayList <JSONObject>();
		try {
			
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db = m.getDB("li328");
			DBCollection coll =db.getCollection("elsayed"); 
			
			for(String key:JSONObject.getNames(jsfriends)){
				
				query.put("login_user",jsfriends.get(key).toString());
			    query2.put("$natural", -1);
			 
			    JSONObject js= new JSONObject();
			 DBCursor cursor = coll.find(query).sort(query2).limit(1);
			 
	

			while (cursor.hasNext()){
				 DBObject o=cursor.next();
				
				 	
				 		
				 		js.put("login",o.get("login_user").toString());
						js.accumulate("text",o.get("text").toString());
						js.accumulate("date",o.get("Date").toString());
						liste.add(js);
			 }
			}
				
			 return liste;
		}	
		catch(Exception e){
			throw new Exception("erreur de bd getComment" +e.getMessage());
		}
	}


	public static ArrayList<JSONObject> getCommentUser() throws Exception {
		JSONObject jsuser=UsersTools.listUser();
		BasicDBObject query = new BasicDBObject ();
		BasicDBObject query2 = new BasicDBObject ();
		ArrayList<JSONObject> liste = new ArrayList <JSONObject>();
		
		try {
			
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db = m.getDB("li328");
			DBCollection coll =db.getCollection("elsayed");  
			
			for(String key:JSONObject.getNames(jsuser)){
				
				query.put("login_user",jsuser.get(key).toString());
			    query2.put("$natural", -1);
			 
			    JSONObject js= new JSONObject();
			 DBCursor cursor = coll.find(query).sort(query2).limit(1);
			 
	

			while (cursor.hasNext()){
				 DBObject o=cursor.next();
				
				 	
				 		
				 		js.put("login",o.get("login_user").toString());
						js.accumulate("text",o.get("text").toString());
						js.accumulate("date",o.get("Date").toString());
						liste.add(js);
			 }
			}
				
			 return liste;
		}	
		catch(Exception e){
			throw new Exception("erreur de bd getCommentUser" +e.getMessage());
		}
	}
	
}

