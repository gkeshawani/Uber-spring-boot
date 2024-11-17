package com.mynewuber.project.uber.UberApp.services;

import org.springframework.data.domain.PageRequest;

import com.mynewuber.project.uber.UberApp.entities.Driver;
import com.mynewuber.project.uber.UberApp.entities.Ride;
import com.mynewuber.project.uber.UberApp.entities.RideRequest;
import com.mynewuber.project.uber.UberApp.entities.Rider;
import com.mynewuber.project.uber.UberApp.entities.enums.RideStatus;


public interface RideService {

    Ride getRideById(Long rideId);

//    void matchWithDrivers(RideRequestDto rideRequestDto);

    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    org.springframework.data.domain.Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    org.springframework.data.domain.Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);
}
