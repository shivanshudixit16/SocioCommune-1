package com.sociocommune.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sociocommune.model.User;

public interface UserRepository extends MongoRepository<User, String>,UserRepositoryCustom {

}