package com.mynewuber.project.uber.UberApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mynewuber.project.uber.UberApp.entities.RideRequest;


@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {

}
