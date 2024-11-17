package com.mynewuber.project.uber.UberApp.services;

import com.mynewuber.project.uber.UberApp.entities.RideRequest;


public interface RideRequestService {
	
	RideRequest findRideRequestById(Long rideRequestId);

	void update(RideRequest rideRequest);

}
