package com.mynewuber.project.uber.UberApp.strategies.impl;

import org.springframework.stereotype.Service;


import com.mynewuber.project.uber.UberApp.entities.RideRequest;
import com.mynewuber.project.uber.UberApp.services.DistanceService;
import com.mynewuber.project.uber.UberApp.strategies.RideFareCalculationStrategy;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class RiderFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());
        return distance*RIDE_FARE_MULTIPLIER;
    }
}
