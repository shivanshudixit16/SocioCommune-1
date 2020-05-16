package com.sociocommune.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sociocommune.model.JobPost;

public interface JobPostRepository extends MongoRepository<JobPost, String> {

}