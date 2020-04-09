package com.sociocommune.repository;

import java.util.List;

import com.sociocommune.model.User;

public interface UserRepositoryCustom 
{
	public User fetchUserByEmail(String email);
	public List<User> findUsers(String s,User user);
}
