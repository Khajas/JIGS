package com.khajas.service;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.khajas.resource.Jigs_db;

@Path("/user")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class AuthenticateUser {
	private String username;
	private String password;
	private Jigs_db mydb=new Jigs_db();
	@GET
	@Path("authenticate/{auth_details}")
	public String userAuthenticated(@PathParam("auth_details") String auth_details) throws SQLException{
		String [] allfields=auth_details.split("_");
		username=allfields[0];
		password=allfields[1];
		mydb.connectToDb();
		// query the database to validate the login
		if(this.mydb.authenticateUser(username, password))
			return "Logged In as "+username+" with pwd: "+password;
		return "Login failed";
	}
	
	@GET
	@Path("register/{reg_details}")
	public String registerUser(@PathParam("reg_details") String reg_details){
		String [] allfields=reg_details.split("_");
		username=allfields[0];
		password=allfields[1];
		mydb.connectToDb();
		if(mydb.addUser(username, password))
			return "Registration successfull";
		return "Registration failed!";
	}
	
	@GET
	@Path("search/{user_name}")
	public String searchUser(@PathParam("user_name") String user_name){
		mydb.connectToDb();
		try {
			if(this.mydb.searchUser(user_name)){
				return "User already exists";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error getting user information: "+e.getMessage();
		}
		return "User: "+user_name+" not found!";
	}
	
}
///////////////////	END OF SOURCE FILE	//////////////////////