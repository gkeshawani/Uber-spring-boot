package com.mynewuber.project.uber.UberApp.services;

import com.mynewuber.project.uber.UberApp.dto.DriverDto;

import com.mynewuber.project.uber.UberApp.dto.SignupDto;
import com.mynewuber.project.uber.UberApp.dto.UserDto;

public interface AuthService {

	
	 String[] login(String email, String password);

	    UserDto signup(SignupDto signupDto);

	    DriverDto onboardNewDriver(Long userId, String vehicleId);

		String refreshToken(String refreshToken);
}
