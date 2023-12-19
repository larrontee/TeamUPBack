package com.alixar.teamup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alixar.teamup.model.TeamEntity;
import com.alixar.teamup.model.UserEntity;
@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
	TeamEntity findByName(String name);

}