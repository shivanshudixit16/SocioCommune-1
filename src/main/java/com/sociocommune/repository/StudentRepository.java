package com.sociocommune.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sociocommune.model.Student;
import com.sociocommune.model.User;

public interface StudentRepository extends MongoRepository<Student, String>,StudentRepositoryCustom {

  
  

}