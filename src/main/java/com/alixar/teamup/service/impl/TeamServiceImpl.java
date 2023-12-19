package com.alixar.teamup.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alixar.teamup.model.TeamEntity;
import com.alixar.teamup.repository.TeamRepository;

@Service
public class TeamServiceImpl {
	@Autowired
	 private TeamRepository teamRepository;
	

	 public TeamEntity getById(Long id) {
	  TeamEntity optionalTeam = teamRepository.findById(id).get();
	  return optionalTeam;
	 }

	 public boolean deleteTeam(Long id) {
	   teamRepository.deleteById(id);
	   return !teamRepository.existsById(id);
	 }

	 public TeamEntity updateTeam(Long id, TeamEntity team) {
	  TeamEntity updateTeam = teamRepository.findById(id).get()	;
	  if(updateTeam!=null  ) {
		  updateTeam.setDescription(team.getDescription());
		  updateTeam.setMiembros(team.getMiembros());
		  updateTeam.setName(team.getName());
		  updateTeam.setPhoto(team.getPhoto());
		  return teamRepository.save(updateTeam);		  
	  }
	  return null;		  

	 }

	 public TeamEntity createTeam(TeamEntity team) {
	  return teamRepository.save(team);
	 }

//	public boolean hacerPrivado(TeamEntity team) {
//		boolean privado = team.getPrivado();
//		if (privado) {
//			return privado;
//		} else if (!privado) {
//			team.setPrivado(true);
//			return team.getPrivado();
//		}
//		return false;
//	}
	public Set<TeamEntity> getAllTeams(){
		Set<TeamEntity> allTeams=(Set<TeamEntity>) teamRepository.findAll();
		return allTeams;
	}

}
