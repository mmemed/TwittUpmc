package services.test;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bd.UsersTools;




public class InserBDTest {

	
	    public static void main(String[] args) throws Exception{
	        // TODO Auto-generated method stub

	        try {

	            Connection connexion=null;
	            PreparedStatement preparedStatement=null;
	            boolean ok=false;


	            connexion = UsersTools.getMySQLConnection();


	            preparedStatement = connexion.prepareStatement( "INSERT INTO User (login,passwd,name,firstName,registerDate) VALUES(?,?,?,?,NOW());" );
	            preparedStatement.setString( 1, "email" );
	            preparedStatement.setString( 2,"password");
	            preparedStatement.setString( 3, "nom" );
	            preparedStatement.setString( 4, "prenom" );

	            int statut = preparedStatement.executeUpdate();
	            if(statut==0) ok=false;
	            if(statut==1) ok=true;

	            System.out.println(ok);

	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();

	        }


	    }

	}

