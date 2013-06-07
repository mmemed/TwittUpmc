package bd;

import java.awt.image.DataBufferShort;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import services.ServicesTools;


public class FriendTools {
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

	public static String loginKey(String key) throws BDException {
		String loginUser="";
		try {
			Connection c = FriendTools.getMySQLConnection();
			Statement req1 = c.createStatement();
			ResultSet rs1 = req1.executeQuery("SELECT login FROM `session` WHERE `key`='"+key+"' AND `expire`=1;");

			while(rs1.next()){
				loginUser = rs1.getString(1);
			}
			return loginUser;
		}
		catch(Exception e){
			throw new BDException("erreur loginKey" +e.getMessage());
		}
	}

	public static void AddFriend(String key, String idFriends) throws BDException {

		String loginUser="";
		int iduser=0;

		try {
			Connection c = FriendTools.getMySQLConnection();
			loginUser=FriendTools.loginKey(key);
			Statement req = c.createStatement();
			ResultSet rs = req.executeQuery("SELECT id FROM `user` WHERE `login`='"+loginUser+"';");
			while(rs.next()){
				iduser = rs.getInt(1);
			}
			req.close();



			Statement st = c.createStatement();
			st.executeUpdate("INSERT into `friends` (idUser,idFriends) values( '" + iduser +"' ,'" + idFriends + "');");
			st.close();
			c.close();
		}catch(Exception e){
			throw new BDException("erreur de bd AddFriend" +e.getMessage());	
		}

	}



	public static void RemoveFriend(String key, String idFriends) throws BDException {
		String loginUser="";
		int iduser=0;

		try {
			Connection c = FriendTools.getMySQLConnection();
			loginUser=FriendTools.loginKey(key);
			Statement req2 = c.createStatement();
			ResultSet rs2 = req2.executeQuery("SELECT id FROM `user` WHERE `login`='"+loginUser+"';");
			while(rs2.next()){
				iduser = rs2.getInt(1);
			}
			req2.close();

			PreparedStatement pstmt = c.prepareStatement("DELETE FROM `friends` WHERE `idUser`='"+iduser+ "' and `idFriends`='"+ idFriends+"';");
			pstmt.executeUpdate();
			pstmt.close();
		}	
		catch(Exception e){
			throw new BDException("erreur de bd RemoveFriend" +e.getMessage());	
		}
	}



	public static boolean FriendExist(String key, String idFriends) throws BDException {
		String loginUser="";
		Boolean val=false;
		int iduser=0;

		try {
			Connection c = FriendTools.getMySQLConnection();
			loginUser=FriendTools.loginKey(key);
			Statement req2 = c.createStatement();
			ResultSet rs2 = req2.executeQuery("SELECT id FROM `user` WHERE `login`='"+loginUser+"';");
			while(rs2.next()){
				iduser = rs2.getInt(1);
			}
			req2.close();

			Statement s =c.createStatement();
			s.executeQuery("Select * FROM `friends` WHERE `idUser`='"+ iduser + "' AND `idFriends`='"+idFriends+"';");
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
			c.close();
		}catch (Exception e){
			throw new BDException("erreur de bd FriendExist "+e.getMessage());
		}
		return val;	

	}



	public static  JSONObject listfriend(String key) throws BDException {
		String loginUser="";
		int iduser=0;
		JSONObject js=new JSONObject();

		try {
			Connection c = FriendTools.getMySQLConnection();
			loginUser=FriendTools.loginKey(key);
			Statement req2 = c.createStatement();
			ResultSet rs2 = req2.executeQuery("SELECT id FROM `user` WHERE `login`='"+loginUser+"';");
			while(rs2.next()){
				iduser = rs2.getInt(1);
			}
			req2.close();

			Statement req3 = c.createStatement();
			ResultSet rs3 = req3.executeQuery("SELECT u.login FROM user u, friends f WHERE f.idUser ="+iduser+" AND f.idFriends = u.id;");
			ArrayList<String> list = new ArrayList<String>();
			while(rs3.next()){
				list.add(rs3.getString(1));
			}
			for(int i=0; i<list.size();i++){
				js.put("login"+i, list.get(i).toString());
			}
			req3.close();
			return js;


		}
		catch (Exception e){
			throw new BDException("erreur de bd ListFriend "+e.getMessage());
		}
	}

	public static JSONObject searchFriend(String friend) throws Exception {
		try{
			JSONObject js = new JSONObject();
			Connection c = FriendTools.getMySQLConnection();
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT `login` FROM user WHERE `login` LIKE '%"+friend+"%'");
			ArrayList<String> list = new ArrayList<String>();
			while(rs.next()){
				list.add(rs.getString(1));
			}
			
			for(int i=0; i<list.size();i++){
				js.put("Amis trouvé "+(i+1)+"", list.get(i).toString());
			}
			st.close();
			
			return js;

		}
		catch (Exception e){
			throw new BDException("erreur de bd SearchFriend "+e.getMessage());
		}

	}
}