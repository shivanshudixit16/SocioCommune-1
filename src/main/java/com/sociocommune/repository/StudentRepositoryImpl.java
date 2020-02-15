package com.sociocommune.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.sociocommune.model.Student;

public class StudentRepositoryImpl implements StudentRepositoryCustom{
	private MongoOperations operations;

	  @Autowired
	  public StudentRepositoryImpl(MongoOperations operations) {
	    this.operations = operations;
	  }

	@Override
	public Student fetchStudentByEmail(String email) {
		Query searchQuery = new Query(); 
		searchQuery.addCriteria(Criteria.where("email").is(email));
		Student student = operations.findOne(searchQuery, Student.class);
		return student;
		
	}

}
