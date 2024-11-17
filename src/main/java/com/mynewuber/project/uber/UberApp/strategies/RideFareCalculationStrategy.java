package com.mynewuber.project.uber.UberApp.strategies;

import com.mynewuber.project.uber.UberApp.entities.RideRequest;

public interface RideFareCalculationStrategy {

	//double calculateFare(RideRequestDto rideRequestDto);

	 double RIDE_FARE_MULTIPLIER = 10;
	double calculateFare(RideRequest rideRequest);
}
