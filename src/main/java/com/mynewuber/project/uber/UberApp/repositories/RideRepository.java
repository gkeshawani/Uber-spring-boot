package com.mynewuber.project.uber.UberApp.repositories;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mynewuber.project.uber.UberApp.entities.Driver;
import com.mynewuber.project.uber.UberApp.entities.Ride;
import com.mynewuber.project.uber.UberApp.entities.Rider;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

	 Page<Ride> findByRider(Rider rider, Pageable pageRequest);

	    Page<Ride> findByDriver(Driver driver, Pageable pageRequest);

}
