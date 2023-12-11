package com.alixar.teamup.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alixar.teamup.dto.CreateUserDTO;
import com.alixar.teamup.dto.UserDTO;
import com.alixar.teamup.enums.UserRoles;
import com.alixar.teamup.model.RoleEntity;
import com.alixar.teamup.model.UserEntity;
import com.alixar.teamup.repository.RoleRepository;
import com.alixar.teamup.repository.UserRepository;
import com.alixar.teamup.service.impl.UserDetailsServiceImpl;
import com.alixar.teamup.service.impl.UserServiceImpl;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
	UserServiceImpl userService;

	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@Validated @RequestBody CreateUserDTO createUserDTO) {

		Set<RoleEntity> roles = createUserDTO.getRoles().stream().map(role -> {
			RoleEntity existingRole = roleRepository.findByName(UserRoles.valueOf(role));
			return existingRole != null ? existingRole : RoleEntity.builder().name(UserRoles.valueOf(role)).build();
		}).collect(Collectors.toSet());

		UserEntity userEntity = UserEntity.builder().username(createUserDTO.getUsername())
				.password(passwordEncoder.encode(createUserDTO.getPassword())).email(createUserDTO.getEmail())
				.name(createUserDTO.getName()).roles(roles).build();

		userRepository.save(userEntity);

		return ResponseEntity.ok(userEntity);
	}

	@GetMapping("/misDatos")
	public UserEntity userDetails(Principal user) {
		return userService.loadUserByUsername(user.getName());
	}

	@GetMapping("/allUsers")
	public ResponseEntity<Set<UserDTO>> allUsers() {
		List<UserEntity> allUsers = userRepository.findAll();
		ModelMapper modelMapper = new ModelMapper();
		Set<UserDTO> userDTOs = allUsers.stream().map(userEntity -> modelMapper.map(userEntity, UserDTO.class))
				.collect(Collectors.toSet());
		return ResponseEntity.ok(userDTOs);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") long userId) {
		UserEntity userEntity = userService.getUserById(userId);
		if (userEntity != null) {
			ModelMapper modelMapper = new ModelMapper();
			UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
			return ResponseEntity.ok(userDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<String> deleteUserById(@PathVariable("userId") long userId) {
		boolean deleted = userService.deleteUser(userId);
		if (deleted) {
			return ResponseEntity.ok("User with ID " + userId + " has been deleted successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/users/{userId}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") long userId, @RequestBody UserDTO user) {
		System.err.println(user);
		System.err.println(userId);
		UserEntity userEntity=userService.getUserById(userId);
		
		if (userEntity != null) {
			userEntity.setUsername(user.getUsername());
			userEntity.setEmail(user.getEmail());
			userEntity.setDescription(user.getDescription());
			userEntity.setProfilePhoto(user.getProfilePhoto());
			userService.updateUser(userEntity);
			ModelMapper modelMapper = new ModelMapper();
			
			UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
			return ResponseEntity.ok(userDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
