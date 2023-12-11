package com.alixar.teamup.controller;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alixar.teamup.model.TeamEntity;
import com.alixar.teamup.repository.TeamRepository;
import com.alixar.teamup.service.impl.TeamServiceImpl;

@RestController
@RequestMapping("/teams")
@CrossOrigin(origins = "*")
public class TeamController {
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private TeamServiceImpl teamService;

	@GetMapping
	public ResponseEntity<Set<TeamEntity>> getAllTeams() {
		Set<TeamEntity> teams = teamService.getAllTeams();
		if (teams == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(teams, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<TeamEntity> createTeam(@RequestBody TeamEntity team) {
	 TeamEntity createdTeam = teamService.createTeam(team);
	 if (createdTeam == null) {
	     return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	 }
	 return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TeamEntity> updateTeam(@PathVariable Long id, @RequestBody TeamEntity team) {
		TeamEntity updatedTeam = teamService.updateTeam(id, team);
		if (updatedTeam == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
	 boolean isDeleted = teamService.deleteTeam(id);
	 if (!isDeleted) {
	     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 }
	 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TeamEntity> getById(@PathVariable Long id) {
		TeamEntity team = teamService.getById(id);
		if (team == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(team, HttpStatus.OK);
	}
}
