package com.sociocommune.repository;

import java.util.List;

import com.sociocommune.model.User;

public interface UserRepositoryCustom {
	public List<User> fetchUserByFNameAndLName(String FName, String Lname);
}
