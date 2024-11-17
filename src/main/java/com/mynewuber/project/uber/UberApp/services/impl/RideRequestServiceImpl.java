package com.mynewuber.project.uber.UberApp.services.impl;

import org.springframework.stereotype.Service;

import com.mynewuber.project.uber.UberApp.entities.RideRequest;
import com.mynewuber.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.mynewuber.project.uber.UberApp.repositories.RideRequestRepository;
import com.mynewuber.project.uber.UberApp.services.RideRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

	private final RideRequestRepository rideRequestRepository;
	
	
	@Override
	public RideRequest findRideRequestById(Long rideRequestId) {
		// TODO Auto-generated method stub
		return rideRequestRepository
				.findById(rideRequestId)
				.orElseThrow(() -> new ResourceNotFoundException("RideRequest Not Found with this Id: " +rideRequestId));

	}


	@Override
	public void update(RideRequest rideRequest) {
		// TODO Auto-generated method stub
		rideRequestRepository.findById(rideRequest.getId())
        .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id: "+rideRequest.getId()));
		rideRequestRepository.save(rideRequest);

		
	}

}
