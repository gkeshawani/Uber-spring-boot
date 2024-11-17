package com.mynewuber.project.uber.UberApp.dto;

import java.time.LocalDateTime;



import com.mynewuber.project.uber.UberApp.entities.enums.PaymentMethod;
import com.mynewuber.project.uber.UberApp.entities.enums.RideStatus;

import lombok.Data;


@Data

public class RideDto {

	
	 private Long id;
	 	
	 private PointDto pickupLocation;  
	    private PointDto dropOffLocation;  
	 	
	 	private LocalDateTime createdTime;
	 	private RiderDto rider;
	 	private DriverDto driver;
	 	
	 	private PaymentMethod paymentMethod;
	 	
	 	private RideStatus rideStatus;
	 	
	 	private String otp;
		
		private Double fare;
		private LocalDateTime startedAt;
		private LocalDateTime endedAt;
}
