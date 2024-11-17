package com.mynewuber.project.uber.UberApp.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mynewuber.project.uber.UberApp.dto.DriverDto;
import com.mynewuber.project.uber.UberApp.dto.RideDto;
import com.mynewuber.project.uber.UberApp.dto.RideRequestDto;
import com.mynewuber.project.uber.UberApp.dto.RiderDto;
import com.mynewuber.project.uber.UberApp.entities.Driver;
import com.mynewuber.project.uber.UberApp.entities.Ride;
import com.mynewuber.project.uber.UberApp.entities.RideRequest;
import com.mynewuber.project.uber.UberApp.entities.Rider;
import com.mynewuber.project.uber.UberApp.entities.User;
import com.mynewuber.project.uber.UberApp.entities.enums.RideRequestStatus;
import com.mynewuber.project.uber.UberApp.entities.enums.RideStatus;
import com.mynewuber.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.mynewuber.project.uber.UberApp.repositories.RideRequestRepository;
import com.mynewuber.project.uber.UberApp.repositories.RiderRepository;
import com.mynewuber.project.uber.UberApp.services.DriverService;
import com.mynewuber.project.uber.UberApp.services.RatingService;
import com.mynewuber.project.uber.UberApp.services.RideService;
import com.mynewuber.project.uber.UberApp.services.RiderService;
import com.mynewuber.project.uber.UberApp.strategies.RideStrategyManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService{

	  private final ModelMapper modelMapper;
	  private final RideStrategyManager rideStrategyManager;
	  private final RideRequestRepository rideRequestRepository;
	  private final RideService rideService;
	  private final DriverService driverService;
	  private final RatingService ratingService;

	    private final RiderRepository riderRepository;
	

	@Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
		Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);
        
        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> driver=  rideStrategyManager
        		.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);
        //TODO : send notification to all the drivers about this ride request

        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }


    @Override
    public RideDto cancelRide(Long rideId) {
    	
    	Rider rider = getCurrentRider();
    	Ride ride = rideService.getRideById(rideId);  
    	
    	if(! rider.equals(ride.getRider())) {
    		throw new  RuntimeException("Rider does not own this Ride with Id:" + rideId);
    	}
    	
    	if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
			throw new RuntimeException("Ride cannot be cancelled, invalid status:" + ride.getRideStatus());
		}
		
		Ride saveRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
		
		driverService.updateDriverAvailability(ride.getDriver(), true);

        return modelMapper.map(saveRide, RideDto.class);
    	
      
    }	
    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if(!rider.equals(ride.getRider())) {
            throw new RuntimeException("Rider is not the owner of this Ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride status is not Ended hence cannot start rating, status: "+ride.getRideStatus());
        }

        return ratingService.rateDriver(ride, rating);
    }


    @Override
    public RiderDto getMyProfile() {
    	Rider currrndtRide = getCurrentRider();
    	return modelMapper.map(currrndtRide, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
    	Rider currrndtRide = getCurrentRider();
	        return rideService.getAllRidesOfRider(currrndtRide, pageRequest).map(
	                ride -> modelMapper.map(ride, RideDto.class)
	        );
    }

    @Override 
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }





	@Override
	public Rider getCurrentRider() {
		// TOO implement spring security
		
	      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	        return riderRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
	                "Rider not associated with user with id: "+user.getId()  ));
	}
}