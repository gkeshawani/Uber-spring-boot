package com.mynewuber.project.uber.UberApp.dto;

import java.time.LocalDateTime;
import com.mynewuber.project.uber.UberApp.entities.enums.PaymentMethod;
import com.mynewuber.project.uber.UberApp.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {


	 private Long id;

	    private PointDto pickupLocation;
	    private PointDto dropOffLocation;
	    private PaymentMethod paymentMethod;

	    private LocalDateTime requestedTime;

	    private RiderDto rider;
	    private Double fare;

	    private RideRequestStatus rideRequestStatus;
	
}
