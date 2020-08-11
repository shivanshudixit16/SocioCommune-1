package com.sociocommune.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.*;

import com.sociocommune.model.JobPost;

public class JobPostRepositoryImpl implements JobPostRepositoryCustom{
	private MongoOperations operations;

	  @Autowired
	  public JobPostRepositoryImpl(MongoOperations operations) {
	    this.operations = operations;
	  }

	@Override
	public List<JobPost> fetchPostByEmail(String email) {
		Query searchQuery = new Query(); 
		searchQuery.addCriteria(Criteria.where("userEmail").is(email));
		List<JobPost> jps = operations.find(searchQuery, JobPost.class);
		return jps;
	}


}
