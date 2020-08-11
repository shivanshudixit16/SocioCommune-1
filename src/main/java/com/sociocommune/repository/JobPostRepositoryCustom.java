package com.sociocommune.repository;

import java.util.List;

import com.sociocommune.model.JobPost;

public interface JobPostRepositoryCustom
{
	public List<JobPost>  fetchPostByEmail(String email);
}