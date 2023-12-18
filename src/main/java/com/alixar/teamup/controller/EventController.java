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
import org.springframework.web.bind.annotation.RestController;

import com.alixar.teamup.dto.EventDTO;
import com.alixar.teamup.enums.UserRoles;
import com.alixar.teamup.model.EventEntity;
import com.alixar.teamup.model.RoleEntity;
import com.alixar.teamup.model.UserEntity;
import com.alixar.teamup.repository.EventRepository;
import com.alixar.teamup.service.impl.EventServiceImpl;
import com.alixar.teamup.service.impl.UserDetailsServiceImpl;
import com.alixar.teamup.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "*")
public class EventController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private EventServiceImpl eventService;

	@GetMapping
	public ResponseEntity<Set<EventEntity>> getAllEvents() {
		Set<EventEntity> events = eventService.getAllEvents();
		if (events == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<EventEntity> createEvent(@RequestBody EventDTO event) {
//		Set<UserEntity> participants = event.getParticipants().stream().map(userDto -> {
//			   UserEntity existingUser = userService.getUserById(userDto.getId());
//			   return existingUser != null ? existingUser : null;
//			}).collect(Collectors.toSet());
		System.err.println(event.getCreator());
				UserEntity creator= userService.getUserById(event.getCreator().getId());
		EventEntity createdEvent = EventEntity.builder().name(event.getName()).creator(creator)
				.description(event.getDescription()).address(event.getAddress()).date(event.getDate())
				.tipoEvent(event.getTipoEvent()).build();
		EventEntity creado=eventService.createEvent(createdEvent);
		if (creado==null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
	}

	@PutMapping()
	public ResponseEntity<EventEntity> updateEvent(@RequestBody EventDTO event) {
		Long id=event.getId();
		Set<UserEntity> participants= event.getParticipants().stream().map(participant -> {
			UserEntity existingUser = userService.getUserById(participant.getId());
			return existingUser != null ? existingUser : null;
		}).collect(Collectors.toSet());
		
		EventEntity eventEntity = EventEntity.builder().id(id).name(event.getName())
				.description(event.getDescription()).address(event.getAddress()).date(event.getDate())
				.tipoEvent(event.getTipoEvent()).participants(participants).build();
		EventEntity updatedEvent = eventService.updateEvent(id, eventEntity);
		if (updatedEvent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
		boolean isDeleted = eventService.deleteEvent(id);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EventEntity> getById(@PathVariable Long id) {
		EventEntity event = eventService.getById(id);
		if (event == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(event, HttpStatus.OK);
	}

}
