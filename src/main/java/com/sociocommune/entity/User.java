package com.sociocommune.entity;

import org.springframework.stereotype.Component;

import org.springframework.data.annotation.Id;
@Component
public class User {
	@Id
	  public String id;

	  public String firstName;
	  public String lastName;

	  public User() {}

	  public User(String firstName, String lastName) {
	    this.firstName = firstName;
	    this.lastName = lastName;
	  }

	  @Override
	  public String toString() {
	    return String.format(
	        "User[id=%s, firstName='%s', lastName='%s']",
	        id, firstName, lastName);
	  }


}
