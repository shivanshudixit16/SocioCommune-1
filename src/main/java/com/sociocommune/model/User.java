package com.sociocommune.model;

import org.springframework.stereotype.Component;

import org.springframework.data.annotation.Id;
@Component
public class User {
	@Id
	public String id;
	public String name;
	public String email;
	public String password;
	public String type;

	public User(String name, String email, String password,String type) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.type = type;
	}
	
	public User() {}
	
	@Override
	public String toString() {
		return String.format(
	        "User[id=%s, Name='%s', Email='%s', Password='%s', Type='%s']",
	        id, name, email, password, type);
	  }


}
