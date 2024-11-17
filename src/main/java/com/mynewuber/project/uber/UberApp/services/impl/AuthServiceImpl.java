package com.mynewuber.project.uber.UberApp.services.impl;

import java.util.Set;


import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import com.mynewuber.project.uber.UberApp.Security.JWTService;
import com.mynewuber.project.uber.UberApp.dto.DriverDto;
import com.mynewuber.project.uber.UberApp.dto.SignupDto;
import com.mynewuber.project.uber.UberApp.dto.UserDto;
import com.mynewuber.project.uber.UberApp.entities.Driver;
import com.mynewuber.project.uber.UberApp.entities.User;
import com.mynewuber.project.uber.UberApp.entities.enums.Role;
import com.mynewuber.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.mynewuber.project.uber.UberApp.exceptions.RuntimeConflictException;
import com.mynewuber.project.uber.UberApp.repositories.UserRepository;
import com.mynewuber.project.uber.UberApp.services.AuthService;
import com.mynewuber.project.uber.UberApp.services.DriverService;
import com.mynewuber.project.uber.UberApp.services.RiderService;
import com.mynewuber.project.uber.UberApp.services.WalletService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final RiderService riderService;
	private final 	WalletService walletService;
	private final DriverService driverService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	
	
	 @Override
	    public String[] login(String email, String password) {
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(email, password)
	        );

	        User user = (User) authentication.getPrincipal();

	        String accessToken = jwtService.generateAccessToken(user);
	        String refreshToken = jwtService.generateRefreshToken(user);

	        return new String[]{accessToken, refreshToken};
	    }

	@Override
	@Transactional
	 public UserDto signup(SignupDto signupDto) {
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if(user != null)
                throw new RuntimeConflictException("Cannot signup, User already exists with email "+signupDto.getEmail());

        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);

//        create user related entities
        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

	@Override
	public DriverDto onboardNewDriver(Long userId, String vehicleId) {
		// TODO Auto-generated method stub
		 User user = userRepository.findById(userId)
	                .orElseThrow(() -> new ResourceNotFoundException("User not found with id "+userId));

		 if(user.getRoles().contains(Role.DRIVER))
	            throw new RuntimeConflictException("User with id "+userId+" is already a Driver");

		 Driver createDriver = Driver.builder()
	                .user(user)
	                .rating(0.0)
	                .vehicleId(vehicleId)
	                .available(true)
	                .build();
	        user.getRoles().add(Role.DRIVER);
	        userRepository.save(user);
	        Driver savedDriver = driverService.createNewDriver(createDriver);
	        return modelMapper.map(savedDriver, DriverDto.class);
		 
	}

	@Override
	public String refreshToken(String refreshToken) {
		 Long userId = jwtService.getUserIdFromToken(refreshToken);
	        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " +
	                "with id: "+userId));

	        return jwtService.generateAccessToken(user);
	}

	
}
