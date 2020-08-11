package com.sociocommune.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.sociocommune.model.Chat;

public class ChatRepositoryImpl implements ChatRepositoryCustom{
	private MongoOperations operations;

	  @Autowired
	  public ChatRepositoryImpl(MongoOperations operations) {
	    this.operations = operations;
	  }


	@Override
	public List<Chat> findChats(String sen,String rec) 
	{
		Query searchQuery = new Query(); 
        searchQuery.addCriteria(Criteria.where("sender").regex(sen));
        searchQuery.addCriteria(Criteria.where("receiver").regex(rec));
		List<Chat> chats= operations.find(searchQuery, Chat.class);
		return chats;
	}

}
