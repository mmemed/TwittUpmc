package bd;

import java.awt.image.DataBufferShort;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import services.ServicesTools;


public class UsersTools {
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

	public static boolean UserExist(String login)  throws BDException, SQLException, ClassNotFoundException{
		
		boolean val=false;
		Connection c = UsersTools.getMySQLConnection();
		
		try {
				String query="Select * FROM user WHERE login=\""+ login + "\";";
				Statement s =c.createStatement();
				s.executeQuery(query);
				ResultSet res= s.getResultSet();
				
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
		}catch (Exception e){
				throw new BDException("erreur de bd UserExist "+e.getMessage());
		}
	finally {
		c.close();
	}
	return val;	

	
	}


	public static void createUser(String login, String password, String nom,
			String prenom, String mail) throws Exception {
		
		try{
		
			Connection c = UsersTools.getMySQLConnection();
		 
		Statement s =c.createStatement();
		String query= "Insert into `user` (login,password,nom,prenom,mail) values ('" + login +"','" + password + "','" + nom + "','" + prenom + "','" + mail +"');" ;
		s.executeUpdate(query);
		s.close();
		c.close();
		}catch(Exception e){
			throw new BDException("erreur de bd createUser" +e.getMessage());	
		}
		
	}

	public static boolean userConnect(String login, String password) throws  BDException {
		boolean val=false;
		
		try{
		Connection c = UsersTools.getMySQLConnection();
		
		
				String query=("SELECT login, password from user Where login='"+login+"' and password='"+password+"' ;");
				Statement s =c.createStatement();
				s.executeQuery(query);
				ResultSet res= s.getResultSet();
				
				if (res.next()){
						res.close();
						s.close();
						c.close();
					  val=true;
				}
				else {
					res.close();
					s.close();
					c.close();
				   val=false;
				}
		}catch (Exception e){
				throw new BDException("erreur de bd UserConnect "+e.getMessage());
		}
	return val;	

	

	}

	public static JSONObject logoutUser(String key) throws BDException{
		boolean val=false;
		JSONObject js=null;
		try{
			Connection c = UsersTools.getMySQLConnection();
			Statement st = c.createStatement();	
			Statement s =c.createStatement();
			String query="Select * FROM `session` WHERE `session`.`key` = '"+key+"';";
			s.executeQuery(query);
			ResultSet res= s.getResultSet();
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
			   if(val==true){
			st.executeUpdate("UPDATE `session` SET `expire`='0'  WHERE `session`.`key` = '"+key+"';");
			st.close();
			c.close();
			js=ServicesTools.ok();
			   }
			   else{
				   js=ServicesTools.error("clé inexistante",10);
			   }
			   return js;
		}catch (Exception e) {
			throw new BDException("erreur de bd LogoutUser "+e.getMessage());
	     }
	}

	public static JSONObject Userkey(String login) throws  BDException{
		
		try{
			JSONObject js = new JSONObject();
			Connection c = UsersTools.getMySQLConnection();
			String key = ServicesTools.key32();
			String query=("SELECT *  from user Where login='"+login+"';");
			Statement s =c.createStatement();
			s.executeQuery(query);
			ResultSet res= s.getResultSet();
			while(res.next()){
				js.put("id",res.getString("id"));
				js.put("login", res.getString("login"));
				js.put("nom", res.getString("nom"));
				js.put("prenom", res.getString("prenom"));
				js.put("key", key);
			}
			String query2= "INSERT into session values ('"+login+"','"+key+"',1, NOW());";
			s.executeUpdate(query2);
			s.close();
			c.close();
			return js;
			}catch(Exception e){
				throw new BDException("erreur de bd Userkey" +e.getMessage());		
			}
		
	}

	public static JSONObject listUser() throws Exception {

		try{
			JSONObject js = new JSONObject();
			ArrayList<String> list = new ArrayList<String>();
			Connection c = UsersTools.getMySQLConnection();
			String key = ServicesTools.key32();
			String query=("SELECT login  from user");
			Statement s =c.createStatement();
			s.executeQuery(query);
			ResultSet res= s.getResultSet();
			while(res.next()){
				list.add(res.getString(1));
				}
				for(int i=0; i<list.size();i++){
					js.put("login"+i, list.get(i).toString());
				}
				res.close();
				s.close();
				c.close();
			return js;
			}catch(Exception e){
				throw new BDException("erreur de bd listUser" +e.getMessage());		
			}
	}

	public static String getid(String logFriends) throws Exception {
		String id="";
		try {
			Connection c = FriendTools.getMySQLConnection();
			Statement req1 = c.createStatement();
			ResultSet rs1 = req1.executeQuery("SELECT id FROM `user` WHERE `login`='"+logFriends+"';");

			while(rs1.next()){
				id = rs1.getString(1);
			}
			return id;
		}
		catch(Exception e){
			throw new BDException("erreur getid" +e.getMessage());
		}
	}
}
