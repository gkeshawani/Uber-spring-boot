package com.mynewuber.project.uber.UberApp.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.mynewuber.project.uber.UberApp.dto.DriverDto;
import com.mynewuber.project.uber.UberApp.dto.RideDto;
import com.mynewuber.project.uber.UberApp.dto.RideRequestDto;
import com.mynewuber.project.uber.UberApp.dto.RiderDto;
import com.mynewuber.project.uber.UberApp.entities.Rider;
import com.mynewuber.project.uber.UberApp.entities.User;

public interface RiderService {

	
	 RideRequestDto requestRide(RideRequestDto rideRequestDto);

	    RideDto cancelRide(Long rideId);

	    DriverDto rateDriver(Long rideId, Integer rating);

	    RiderDto getMyProfile();

	    Page<RideDto> getAllMyRides(PageRequest pageRequest);

	    Rider createNewRider(User user);

	    Rider getCurrentRider();
	
	
}
