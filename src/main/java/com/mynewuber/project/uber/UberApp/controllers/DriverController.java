package com.mynewuber.project.uber.UberApp.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mynewuber.project.uber.UberApp.dto.DriverDto;
import com.mynewuber.project.uber.UberApp.dto.RatingDto;
import com.mynewuber.project.uber.UberApp.dto.RideDto;
import com.mynewuber.project.uber.UberApp.dto.RideStartDto;
import com.mynewuber.project.uber.UberApp.dto.RiderDto;
import com.mynewuber.project.uber.UberApp.services.DriverService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
@Secured("ROLE_DRIVER")
public class DriverController {
	
	private final DriverService driverService;
	
	 @PostMapping("/acceptRide/{rideRequestId}")
	    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId) {
	        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
	    }
	
	 @PostMapping("/startRide/{rideRequestId}")
	    public ResponseEntity<RideDto> startRide(@PathVariable Long rideRequestId, @RequestBody RideStartDto rideStartDto) {
	        return ResponseEntity.ok(driverService.startRide(rideRequestId,rideStartDto.getOtp()));
	    }
	 
	 @PostMapping("/endRide/{rideId}")
	    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId) {
	        return ResponseEntity.ok(driverService.endRide(rideId));
	    }

	 

	    @PostMapping("/cancelRide/{rideId}")
	    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId) {
	        return ResponseEntity.ok(driverService.cancelRide(rideId));
	    }

	    @PostMapping("/rateRider")
	    public ResponseEntity<RiderDto> rateRider(@RequestBody RatingDto ratingDto) {
	        return ResponseEntity.ok(driverService.rateRider(ratingDto.getRideId(), ratingDto.getRating()));
	    }

	    @GetMapping("/getMyProfile")
	    public ResponseEntity<DriverDto> getMyProfile() {
	        return ResponseEntity.ok(driverService.getMyProfile());
	    }

	    @GetMapping("/getMyRides")
	    public ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffset,
	                                                       @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
	        PageRequest pageRequest = PageRequest.of(pageOffset, pageSize,
	        	Sort.by(Sort.Direction.DESC, "createdTime", "id"));
	        return ResponseEntity.ok(driverService.getAllMyRides(pageRequest));
	    }

//	    @PostMapping("/rateRider/{rideId}/{rating}")
//	    public ResponseEntity<RiderDto> rateRider(@PathVariable Long rideId, @PathVariable Integer rating) {
//	        return ResponseEntity.ok(driverService.rateRider(rideId, rating));
//	    }

	 
}
