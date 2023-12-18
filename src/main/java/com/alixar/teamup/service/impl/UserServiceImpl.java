package com.alixar.teamup.service.impl;

import java.io.FileNotFoundException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alixar.teamup.model.EventEntity;
import com.alixar.teamup.model.TeamEntity;
import com.alixar.teamup.model.UserEntity;
import com.alixar.teamup.repository.EventRepository;
import com.alixar.teamup.repository.TeamRepository;
import com.alixar.teamup.repository.UserRepository;
import com.alixar.teamup.service.UserService;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final EventRepository eventRepository;
	private final TeamRepository teamRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, EventRepository eventRepository, TeamRepository teamRepository) {
		this.userRepository = userRepository;
		this.eventRepository = eventRepository;
		this.teamRepository = teamRepository;
	}

	@Override
	public UserEntity getUserById(Long id) {
		UserEntity userOptional = userRepository.findById(id).get();
		return userOptional;
	}

	@Override
	public void updateUser(UserEntity updatedUser) {
		Long userId = updatedUser.getId();
		Boolean userExits = userRepository.existsById(userId);
		UserEntity usernameExits = userRepository.findByUsername(updatedUser.getUsername());

		if (userExits) {
			userRepository.save(updatedUser);
		} else {
			throw new RuntimeException("Usuario no encontrado con ID: " + userId);
		}
	}

	@Override
	public boolean deleteUser(Long id) {
		Boolean userExits = userRepository.existsById(id);

		if (userExits) {
			userRepository.deleteById(id);
			return true;
		} else {
			throw new RuntimeException("Usuario no encontrado con ID: " + id);
		}
	}

	@Override
	public void createUser(UserEntity user) {
		UserEntity existUser = userRepository.findByUsername(user.getUsername());
		if (existUser==null) {
			userRepository.save(user);
		}
		throw new EntityExistsException();

	}

	@Override
	public UserEntity loadUserByUsername(String username) {
		UserEntity user = userRepository.findByUsername(username);
		if (user != null) {
			return user;
		} else {
			throw new RuntimeException("Usuario no encontrado con Username: " + username);
		}
	}

	public boolean isCreator(UserEntity user, EventEntity event) {
		UserEntity creator = event.getCreator();
		if (creator.getId() == user.getId()) {
			return true;
		}
		return false;
	}

	public boolean isCreator(UserEntity user, TeamEntity team) {
		UserEntity creator = team.getCreator();
		if (creator.getId() == user.getId()) {
			return true;
		}
		return false;
	}

//	public boolean isLibre(UserEntity user, TeamEntity team) {
//		Set<UserEntity>miembros=team.getMiembros();
//		if(miembros.contains(user)) {
//			return false;			
//		}
//		return true;
//	}

	public boolean checkUserEvent(Long userId, EventEntity event) {
		UserEntity user= getUserById(userId);
		Set<UserEntity> participantes = event.getParticipants();
		if (participantes.contains(user)) {
			return true;
		}
		return false;
	}

	public TeamEntity abandonar(UserEntity user, TeamEntity team) throws FileNotFoundException {
		Set<UserEntity> miembros = team.getMiembros();
		if (miembros.contains(user)) {
			miembros.remove(user);
			team.setMiembros(miembros);
			teamRepository.save(team);
			return team;
		}
		throw new FileNotFoundException();
	}

	public EventEntity abandonar(UserEntity user, EventEntity event) throws FileNotFoundException {
		Set<UserEntity> participantes = event.getParticipants();
		if (participantes.contains(user)) {
			participantes.remove(user);
			event.setParticipants(participantes);
			eventRepository.save(event);
			return event;
		}
		throw new FileNotFoundException();
	}

	public TeamEntity unirme(UserEntity user, TeamEntity team) throws FileNotFoundException {
		Set<UserEntity> miembros = team.getMiembros();
		if (!miembros.contains(user)) {
			miembros.add(user);
			team.setMiembros(miembros);
			teamRepository.save(team);
			return team;
		}
		throw new EntityExistsException();
	}

	public EventEntity unirme(UserEntity user, EventEntity event) throws FileNotFoundException {
		Set<UserEntity> miembros = event.getParticipants();
		
		if (!miembros.contains(user)) {
			miembros.add(user);
			event.setParticipants(miembros);
			eventRepository.save(event);
			return event;
		}
		throw new EntityExistsException();
	}

	
    public UserEntity getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
        return user;
	}
}
