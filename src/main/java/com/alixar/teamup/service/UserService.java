package com.alixar.teamup.service;

import com.alixar.teamup.model.UserEntity;

public interface UserService {
	   UserEntity getUserById(Long id);

	    void createUser(UserEntity user);

	    void updateUser(UserEntity user);

	    boolean deleteUser(Long id);

		UserEntity loadUserByUsername(String username);
}
