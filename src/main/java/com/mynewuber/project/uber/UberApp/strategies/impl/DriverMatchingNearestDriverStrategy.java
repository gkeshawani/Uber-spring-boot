package com.mynewuber.project.uber.UberApp.strategies.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.mynewuber.project.uber.UberApp.entities.Driver;
import com.mynewuber.project.uber.UberApp.entities.RideRequest;
import com.mynewuber.project.uber.UberApp.repositories.DriverRepository;
import com.mynewuber.project.uber.UberApp.strategies.DriverMatchingStrategy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenNearestDrivers(rideRequest.getPickupLocation());
    }
}
