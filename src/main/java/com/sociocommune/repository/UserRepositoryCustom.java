package com.sociocommune.repository;

import com.sociocommune.model.User;

public interface UserRepositoryCustom {
	public User fetchUserByEmail(String email);
}
