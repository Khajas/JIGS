package com.khajas.resource;

public class Message {
	private String sender;
	private final int id;
	private String recepient;
	private String message;
	public Message(int id){
		this.id=id;
	}
	
	public void setSender(String sender){
		this.sender=sender;
	}
	
	public void setRecepient(String recepient){
		this.recepient=recepient;
	}
	
	public void setMessage(String message){
		this.message=message;
	}
	
	public String getSender() {
		return sender;
	}
	public int getId() {
		return id;
	}
	public String getRecepient() {
		return recepient;
	}
	public String getMessage() {
		return message;
	}
}
