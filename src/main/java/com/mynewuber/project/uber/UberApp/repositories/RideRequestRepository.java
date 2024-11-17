package com.mynewuber.project.uber.UberApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mynewuber.project.uber.UberApp.entities.RideRequest;

public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {

}
