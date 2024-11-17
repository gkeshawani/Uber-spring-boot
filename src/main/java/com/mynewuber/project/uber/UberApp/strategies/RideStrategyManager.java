package com.mynewuber.project.uber.UberApp.strategies;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.mynewuber.project.uber.UberApp.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.mynewuber.project.uber.UberApp.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.mynewuber.project.uber.UberApp.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import com.mynewuber.project.uber.UberApp.strategies.impl.RiderFareDefaultFareCalculationStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {
	
	private final DriverMatchingHighestRatedDriverStrategy highestRatedDriverStrategy;
	private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
	private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;
	private final RiderFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;
	
	
	public DriverMatchingStrategy driverMatchingStrategy(double riderRating) {
		if(riderRating >= 4.8) {
			return highestRatedDriverStrategy;
		}
		else {
			return nearestDriverStrategy;
		}
		
	}
	
	public RideFareCalculationStrategy rideFareCalculationStrategy() {
		
		LocalTime surgeStartTime  = LocalTime.of(18, 0);
		LocalTime surgeEndTime = LocalTime.of(21, 0);
		LocalTime currentTime = LocalTime.now();
		
		boolean isSurgeTime = 	currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);
		
		if(isSurgeTime) {
			return surgePricingFareCalculationStrategy;
			
		}
		else {
			return defaultFareCalculationStrategy;
		}
		
	}
	

}
