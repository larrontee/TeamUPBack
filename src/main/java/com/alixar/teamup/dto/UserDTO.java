package com.alixar.teamup.dto;
import java.time.LocalDate;
import java.util.Set;

import com.alixar.teamup.model.RoleEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	   private Long id;
	   private String username;
	   private String password;
	   private String name;
	   private String email;
	   private String profilePhoto;
	   private String description;
	   private Set<RoleDTO> roles;
}
