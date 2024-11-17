package com.mynewuber.project.uber.UberApp.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mynewuber.project.uber.UberApp.dto.DriverDto;
import com.mynewuber.project.uber.UberApp.dto.RiderDto;
import com.mynewuber.project.uber.UberApp.entities.Driver;
import com.mynewuber.project.uber.UberApp.entities.Rating;
import com.mynewuber.project.uber.UberApp.entities.Ride;
import com.mynewuber.project.uber.UberApp.entities.Rider;
import com.mynewuber.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.mynewuber.project.uber.UberApp.exceptions.RuntimeConflictException;
import com.mynewuber.project.uber.UberApp.repositories.DriverRepository;
import com.mynewuber.project.uber.UberApp.repositories.RatingRepository;
import com.mynewuber.project.uber.UberApp.repositories.RiderRepository;
import com.mynewuber.project.uber.UberApp.services.RatingService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Override
    public DriverDto rateDriver(Ride ride, Integer rating) {
        Driver driver = ride.getDriver();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with id: "+ride.getId()));

        if(ratingObj.getDriverRating() != null)
            throw new RuntimeConflictException("Driver has already been rated, cannot rate again");

        ratingObj.setDriverRating(rating);

        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average().orElse(0.0);
        driver.setRating(newRating);

        Driver savedDriver = driverRepository.save(driver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }

	@Override
	public RiderDto rateRider(Ride ride, Integer rating) {
		// TODO Auto-generated method stub
		 Rider rider = ride.getRider();
	        Rating ratingObj = ratingRepository.findByRide(ride)
	                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with id: "+ride.getId()));
	        if(ratingObj.getRiderRating() != null)
	            throw new RuntimeConflictException("Rider has already been rated, cannot rate again");

	        ratingObj.setRiderRating(rating);

	        ratingRepository.save(ratingObj);

	        Double newRating = ratingRepository.findByRider(rider)
	                .stream()
	                .mapToDouble(Rating::getRiderRating)
	                .average().orElse(0.0);
	        rider.setRating(newRating);

	        Rider savedRider = riderRepository.save(rider);
	        return modelMapper.map(savedRider, RiderDto.class);
	}


    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();
        ratingRepository.save(rating);
    }
	

}
