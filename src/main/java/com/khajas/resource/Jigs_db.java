package com.khajas.resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;

public class Jigs_db {
	private Connection connection;
	private Statement smt;
	
	public boolean connectToDb(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		try {
			connection=DriverManager.getConnection("jdbc:mysql://107.180.6.46:3306/jigs_db?user=khajas&password=zaffar");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		try {
			if(connection.isClosed()){
				System.out.println("Can't connect to database");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean authenticateUser(String username, String password) throws SQLException{
        smt=connection.createStatement();
        final ResultSet rs=smt.executeQuery("select password from users where username='"+username+"';");
        while(rs.next()){
        	System.out.println(rs.getString("password"));
            if(rs.getString("password").equals(password)){
            	System.out.println("Authentication passed");
            	return true;
            }
        }
        smt.close();
        System.out.println("Authentication failed");
		return false;
	}
	
	public boolean searchUser(String username) throws SQLException{
		smt=connection.createStatement();
		final ResultSet rs=smt.executeQuery("select * from users where username='"+username+"';");
		while(rs.next()){
			if(rs.getString("username").equals(username))
				return true;
		}
		return false;
	}
	
	public boolean addUser(String username, String password){
        try {
			final PreparedStatement psmt=(PreparedStatement) connection.prepareStatement(""
					+ "insert into users(username, password) values('"+username+"','"+password+"')");
			psmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
        return true;
	}
	
	public boolean closeConnection(){
		try {
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean sendMessage(String sender, String recepient, String message){
        try {
			final PreparedStatement psmt=(PreparedStatement) connection.prepareStatement(""
					+ "insert into messages(sender, recepient, message, messageRead) values('"
					+sender+"','"+recepient+"','"+message+"','NO');");
			psmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
        return true;
	}
	
	public Map<Integer, Message> retreiveMessages(String username){
		System.out.println("Searching for message for user: "+username);
		Map<Integer, Message> messages=new HashMap<>();
		try {
			this.connectToDb();
	        smt=connection.createStatement();
			final ResultSet rs=smt.executeQuery("select ID, sender, message from messages where recepient='"+username+"' AND messageRead='NO';");
			while(rs.next()){
				String sender=rs.getString("sender");
				String message=rs.getString("message");
				System.out.println(sender);
				Message msg=new Message(rs.getInt("ID"));
				msg.setRecepient(username);
				msg.setSender(sender);
				msg.setMessage(message);
				messages.put(rs.getInt("ID"), msg);
				this.markAsRead(rs.getInt("ID"), username);
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}
		return messages;
	}
	
	private void markAsRead(int id, String recepient){
		try{
			this.connectToDb();
			final PreparedStatement pmst=(PreparedStatement) connection.prepareStatement(
					"update messages set messageRead='YES' where messageRead='NO' AND recepient='"+recepient+"' AND ID="+id+";");
			pmst.execute();
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
