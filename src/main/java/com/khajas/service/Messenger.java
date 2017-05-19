package com.khajas.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.khajas.resource.Jigs_db;
import com.khajas.resource.Message;

@Path("/messages")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class Messenger {
	private Jigs_db mydb=new Jigs_db();
	@GET
	@Path("sendmessage/from/{sender}/to/{recepient}/message/{message_text}")
	public String sendMessage(@PathParam("sender") String sender,
							@PathParam("recepient") String recepient,
							@PathParam("message_text") String message_text){
		System.out.printf("User would like to send: "+message_text+""
				+ "To: "+recepient
				+"From: "+sender);
		mydb.connectToDb();
		message_text=message_text.replaceAll("\\_", " ");
		if(mydb.sendMessage(sender, recepient, message_text))
			return "Message sent!";
		return "Failed to send message!";
	}
	@GET
	@Path("/retreive/{user_name}")
	public String retreiveMessages(@PathParam("user_name") String username){
		mydb.connectToDb();
		HashMap<Integer, Message> messages = (HashMap<Integer, Message>) mydb.retreiveMessages(username);
		String message="";
		if(messages.size()==0) message="No new messages!";
		for(Map.Entry<Integer, Message> e:messages.entrySet()){
			message+=("From: "+e.getValue().getSender()+"\nMessage: "
						+e.getValue().getMessage()+"\n");
		}
		return message;
	}
	
}
//////////////	END OF SOURCE FILE	////////////////