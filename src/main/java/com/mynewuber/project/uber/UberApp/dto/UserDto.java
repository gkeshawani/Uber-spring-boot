
package com.mynewuber.project.uber.UberApp.dto;


import java.util.Set;

import com.mynewuber.project.uber.UberApp.entities.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Long id;
	private String name;
	private String email;
	private Set<Role> roles;
	}
