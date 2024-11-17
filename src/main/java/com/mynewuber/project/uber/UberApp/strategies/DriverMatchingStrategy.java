package com.mynewuber.project.uber.UberApp.strategies;

import java.util.List;

import com.mynewuber.project.uber.UberApp.entities.Driver;
import com.mynewuber.project.uber.UberApp.entities.RideRequest;

public interface DriverMatchingStrategy {

	

	public List<Driver> findMatchingDriver(RideRequest rideRequest);
}
