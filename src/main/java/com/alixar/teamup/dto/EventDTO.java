package com.alixar.teamup.dto;

import java.time.LocalDate;
import java.util.Set;

import com.alixar.teamup.model.TeamEntity;
import com.alixar.teamup.model.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {
	private Long id;
	private String name;
	private UserEntity creator;
	private String description;
	private LocalDate date;
	private String address;
	private String tipoEvent;
	private String location;
	private Set<UserDTO> participants;
}
