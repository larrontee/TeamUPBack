package com.alixar.teamup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alixar.teamup.enums.UserRoles;
import com.alixar.teamup.model.RoleEntity;
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	  RoleEntity findByName(UserRoles name);

}