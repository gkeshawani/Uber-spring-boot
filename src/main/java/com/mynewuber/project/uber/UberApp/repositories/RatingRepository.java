package com.mynewuber.project.uber.UberApp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mynewuber.project.uber.UberApp.entities.Driver;
import com.mynewuber.project.uber.UberApp.entities.Rating;
import com.mynewuber.project.uber.UberApp.entities.Ride;
import com.mynewuber.project.uber.UberApp.entities.Rider;

@Repository
public interface RatingRepository  extends JpaRepository<Rating, Long>{

	List<Rating> findByRider(Rider rider);
	List<Rating> findByDriver(Driver driver);
	Optional<Rating> findByRide(Ride ride);
}
