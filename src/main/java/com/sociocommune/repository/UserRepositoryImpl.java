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
	public User fetchUserByEmail(String email) {
		Query searchQuery = new Query(); 
		searchQuery.addCriteria(Criteria.where("email").is(email));
		User user = operations.findOne(searchQuery, User.class);
		return user;
	}

	@Override
	public List<User> findUsers(String s,User user) 
	{
		Query searchQuery = new Query(); 
		searchQuery.addCriteria(Criteria.where("name").regex(s));
		List<User> users= operations.find(searchQuery, User.class);
		users.remove(user);
		return users;
	}

}
