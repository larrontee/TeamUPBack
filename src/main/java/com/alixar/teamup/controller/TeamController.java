package com.alixar.teamup.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alixar.teamup.dto.TeamDTO;
import com.alixar.teamup.model.TeamEntity;
import com.alixar.teamup.model.UserEntity;
import com.alixar.teamup.repository.TeamRepository;
import com.alixar.teamup.service.impl.TeamServiceImpl;
import com.alixar.teamup.service.impl.UserDetailsServiceImpl;
import com.alixar.teamup.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/teams")
@CrossOrigin(origins = "*")
public class TeamController {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private UserServiceImpl userService;

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
	public ResponseEntity<TeamEntity> createTeam(@RequestBody TeamDTO team) {
		UserEntity creator = userService.getUserById(team.getCreator().getId());
		
			TeamEntity createdTeam = TeamEntity.builder().name(team.getName()).creator(creator)
					.description(team.getDescription()).foundation(team.getFoundation()).build();
			if (teamRepository.findByName(team.getName()) == null) {
			TeamEntity created = teamService.createTeam(createdTeam);
			if (created == null) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping()
	public ResponseEntity<TeamEntity> updateTeam(@RequestBody TeamDTO team) {
		Long id = team.getId();
		Set<UserEntity> participants = team.getMiembros().stream().map(participant -> {
			UserEntity existingUser = userService.getUserById(participant.getId());
			return existingUser != null ? existingUser : null;
		}).collect(Collectors.toSet());
		TeamEntity teamEntity = TeamEntity.builder().id(id).name(team.getName()).description(team.getDescription())
				.miembros(participants).build();
		TeamEntity updatedTeam = teamService.updateTeam(id, teamEntity);
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

	@GetMapping("/name/{teamName}")
	public ResponseEntity<TeamEntity> findByName(@PathVariable String teamName) {
		TeamEntity team = teamRepository.findByName(teamName);
		if (team == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(team, HttpStatus.OK);
	}

}
