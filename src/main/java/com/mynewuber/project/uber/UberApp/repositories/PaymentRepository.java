package com.mynewuber.project.uber.UberApp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mynewuber.project.uber.UberApp.entities.Payment;
import com.mynewuber.project.uber.UberApp.entities.Ride;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

	Optional<Payment> findByRide(Ride ride);

}
