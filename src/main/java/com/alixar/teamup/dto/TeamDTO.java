package com.alixar.teamup.dto;

import java.time.LocalDate;
import java.util.Set;

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
public class TeamDTO {
	private Long id;
	private String name;
	private UserDTO creator;
	private String photo;
	private LocalDate foundation;
	private String description;
	private Set<UserDTO> miembros;
}