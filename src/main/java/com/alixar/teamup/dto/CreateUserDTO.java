package com.alixar.teamup.dto;

import java.time.LocalDate;
import java.util.Set;

import org.antlr.v4.runtime.misc.NotNull;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

	@NonNull
	private String username;
	@NonNull
	private String password;
	@NonNull
	private String email;
	@NonNull
	private String name;
	@NonNull
	private String surnames;
	@NonNull
	private LocalDate birthdate;

	@NonNull
	private Set<String> roles;
}

