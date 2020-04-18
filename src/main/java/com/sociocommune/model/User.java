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
	public String followers;
	public String following;
	public int followingcount;
	public int followercount;
	
	
	public User(String name, String email, String password, String type, String followers, String following,
			int followingcount, int followercount) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.type = type;
		this.followers = followers;
		this.following = following;
		this.followingcount = followingcount;
		this.followercount = followercount;
	}
	
	
	public void copy(User user)
	{
		this.id =user.id;
		this.name=user.name;
		this.email =user.email;
		this.password =user.password;
		this.type =user.type;
		this.followers =user.followers;
		this.following =user.following;
		this.followercount =user.followercount;
		this.followingcount =user.followingcount;
	}

	public User() {}
	
	@Override
	public String toString() {
		return String.format(
	        "User[id=%s, Name='%s', Email='%s', Password='%s', Type='%s' Followers='%d' , Following='%d' ]",
	        id, name, email, password, type,followercount,followercount);
	  }


}
