package com.mynewuber.project.uber.UberApp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.mynewuber.project.uber.UberApp.dto.DriverDto;
import com.mynewuber.project.uber.UberApp.dto.RideDto;
import com.mynewuber.project.uber.UberApp.dto.RiderDto;
import com.mynewuber.project.uber.UberApp.entities.Driver;

public interface DriverService {

	
	RideDto acceptRide(Long rideRequestId);
	RideDto cancelRide(Long rideId);
	RideDto startRide(Long rideId, String otp);
	RideDto endRide(Long rideId);
	 RiderDto rateRider(Long rideId, Integer rating);
	
	DriverDto getMyProfile();
	Page<RideDto> getAllMyRides(PageRequest pageRequest);
	Driver getCurrentDriver();
	Driver updateDriverAvailability(Driver driver, boolean available);
	
	Driver createNewDriver(Driver driver);
	
}
