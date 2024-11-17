package com.mynewuber.project.uber.UberApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
	
	private Long id;
	private UserDto user;
	private Double rating;

	 private String vehicleId;
	
	 private Boolean available;
}
