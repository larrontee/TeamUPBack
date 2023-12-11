package com.alixar.teamup.dto;

import com.alixar.teamup.enums.UserRoles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO {
 private Long id;
 private UserRoles name;
}
