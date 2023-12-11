package com.alixar.teamup.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alixar.teamup.dto.RoleDTO;
import com.alixar.teamup.model.RoleEntity;
import com.alixar.teamup.repository.RoleRepository;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "*")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("/allRoles")
	public ResponseEntity<Set<RoleDTO>> allRoles() {
		List<RoleEntity> allRoles = roleRepository.findAll();
		ModelMapper modelMapper = new ModelMapper();
		Set<RoleDTO> roleDTOs = allRoles.stream().map(roleEntity -> modelMapper.map(roleEntity, RoleDTO.class))
				.collect(Collectors.toSet());
		return ResponseEntity.ok(roleDTOs);
	}

}
