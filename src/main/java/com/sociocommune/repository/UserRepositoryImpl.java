package com.sociocommune.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.sociocommune.model.User;

public class UserRepositoryImpl implements UserRepositoryCustom{
	private MongoOperations operations;

	  @Autowired
	  public UserRepositoryImpl(MongoOperations operations) {
	    this.operations = operations;
	  }

	@Override
	public List<User> fetchUserByFNameAndLName(String FName, String Lname) {
		Query searchQuery = new Query(); 
		searchQuery.addCriteria(Criteria.where("firstName").is(FName));
		List<User> people = operations.find(searchQuery, User.class);
		return people;
		
	}


}
