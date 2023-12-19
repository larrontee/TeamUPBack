package com.alixar.teamup;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alixar.teamup.model.RoleEntity;
import com.alixar.teamup.service.UserService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class TeamUp1Application {
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(TeamUp1Application.class, args);
	}

	@PostConstruct
	public void init() {
		
	}
}
