package com.khajas.resource;

public class User {
	@SuppressWarnings("unused")
	private final String name;
	@SuppressWarnings("unused")
	private final String password;
	public User(String name, String password){
		this.name=name;
		this.password=password;
	}
	public boolean authenticate(){
		return true;
	}
	public boolean addUser(){
		return true;
	}
	public boolean removeUser(){
		return true;
	}
}
