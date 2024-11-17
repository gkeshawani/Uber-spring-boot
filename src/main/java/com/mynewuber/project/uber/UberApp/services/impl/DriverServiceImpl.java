package com.mynewuber.project.uber.UberApp.services.impl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mynewuber.project.uber.UberApp.dto.DriverDto;
import com.mynewuber.project.uber.UberApp.dto.RideDto;
import com.mynewuber.project.uber.UberApp.dto.RiderDto;
import com.mynewuber.project.uber.UberApp.entities.Driver;
import com.mynewuber.project.uber.UberApp.entities.Ride;
import com.mynewuber.project.uber.UberApp.entities.RideRequest;
import com.mynewuber.project.uber.UberApp.entities.User;
import com.mynewuber.project.uber.UberApp.entities.enums.RideRequestStatus;
import com.mynewuber.project.uber.UberApp.entities.enums.RideStatus;
import com.mynewuber.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.mynewuber.project.uber.UberApp.repositories.DriverRepository;
import com.mynewuber.project.uber.UberApp.services.DriverService;
import com.mynewuber.project.uber.UberApp.services.PaymentService;
import com.mynewuber.project.uber.UberApp.services.RatingService;
import com.mynewuber.project.uber.UberApp.services.RideRequestService;
import com.mynewuber.project.uber.UberApp.services.RideService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl  implements DriverService{
	
	private final RideRequestService rideRequestService;
	private final DriverRepository driverRepository;
	private final RideService rideService;
	private final ModelMapper modelMapper;
	private final PaymentService paymentService;
	private final RatingService ratingService;
	

	@Override
	@Transactional
	public RideDto acceptRide(Long rideRequestId) {
		// TODO Auto-generated method stub
		 RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);

	        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
	            throw new RuntimeException("RideRequest cannot be accepted, status is "+ rideRequest.getRideRequestStatus());
	        }
		 
		 Driver currentDriver = getCurrentDriver();
		 if(!currentDriver.getAvailable()) {
			 throw new RuntimeException("Driver can not accept Due to availibility");
		 }
		 
		Driver saveDriver = updateDriverAvailability(currentDriver, false);
		// rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
		 Ride ride = rideService.createNewRide(rideRequest, saveDriver);
		 
		return modelMapper.map(ride, RideDto.class);
		 
	}

	@Override
	public RideDto cancelRide(Long rideId) {
		// TODO Auto-generated method stub
		Ride ride = rideService.getRideById(rideId);
		
		Driver driver = getCurrentDriver();
		
		if(!driver.equals(ride.getDriver())) {
			throw  new RuntimeException("Driver can start a ride as he not accepted it earlier");
		}
		
		if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
			throw new RuntimeException("Ride cannot be cancelled, invalid status:" + ride.getRideStatus());
		}
		

        rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        updateDriverAvailability(driver, true);

        return modelMapper.map(ride, RideDto.class);
	}

	@Override
	public RideDto startRide(Long rideId, String otp) {
		// TODO Auto-generated method stub
		Ride ride = rideService.getRideById(rideId);
		Driver driver = getCurrentDriver();
		
		if(!driver.equals(ride.getDriver())) {
			throw  new RuntimeException("Driver can start a ride as he not accepted it earlier");
		}
		
		if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
			throw new RuntimeException("Ride status is not confirmed hance driver can not start a ride status:" + ride.getRideStatus());
		}
		
		if(!otp.equals(ride.getOtp())) {
			throw new RuntimeException("Otp is not valid , otp: "+ otp);
		}

		ride.setStartedAt(LocalDateTime.now());
		Ride saveRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);
		
		paymentService.createNewPayment(saveRide);
		ratingService.createNewRating(saveRide);
		return modelMapper.map(saveRide, RideDto.class);
	}

	@Override
	@Transactional
	public RideDto endRide(Long rideId) {
		// TODO Auto-generated method stub
		Ride ride = rideService.getRideById(rideId);
		Driver driver = getCurrentDriver();
		
		if(!driver.equals(ride.getDriver())) {
			throw  new RuntimeException("Driver can start a ride as he not accepted it earlier");
		}
		
		if(!ride.getRideStatus().equals(RideStatus.ONGOING)) {
			throw new RuntimeException("Ride status is not ongoing hance driver cannot be ended , status:" + ride.getRideStatus());
		}
		
		ride.setEndedAt(LocalDateTime.now());
		Ride saveRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
		updateDriverAvailability(driver, true);
		
		return modelMapper.map(saveRide, RideDto.class);
	
		
		
	}

	@Override
	public RiderDto rateRider(Long rideId, Integer rating) {
		 Ride ride = rideService.getRideById(rideId);
	        Driver driver = getCurrentDriver();

	        if(!driver.equals(ride.getDriver())) {
	            throw new RuntimeException("Driver is not the owner of this Ride");
	        }

	        if(!ride.getRideStatus().equals(RideStatus.ENDED)) {
	            throw new RuntimeException("Ride status is not Ended hence cannot start rating, status: "+ride.getRideStatus());
	        }

	        return ratingService.rateRider(ride, rating);
	    }

	@Override
	public DriverDto getMyProfile() {
		// TODO Auto-generated method stub
		Driver currendriver = getCurrentDriver();
		
		return modelMapper.map(currendriver, DriverDto.class);
	}

	 @Override
	    public org.springframework.data.domain.Page<RideDto> getAllMyRides(PageRequest pageRequest) {
	        Driver currentDriver = getCurrentDriver();
	        return rideService.getAllRidesOfDriver(currentDriver, pageRequest).map(
	                ride -> modelMapper.map(ride, RideDto.class)
	        );
	    }

	@Override
	public Driver getCurrentDriver() {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return driverRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("Driver not associated with user with Id"+ user.getId()));
	}
 
	@Override
	public Driver updateDriverAvailability(Driver driver, boolean available) {
		// TODO Auto-generated method stub
		driver.setAvailable(available);
        return driverRepository.save(driver);
	}

	@Override
	public Driver createNewDriver(Driver driver) {
		// TODO Auto-generated method stub
		return driverRepository.save(driver);
	}

}
