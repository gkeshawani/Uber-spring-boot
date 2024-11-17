package com.mynewuber.project.uber.UberApp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mynewuber.project.uber.UberApp.entities.Rider;
import com.mynewuber.project.uber.UberApp.entities.User;

public interface RiderRepository extends JpaRepository<Rider, Long>{

	Optional<Rider> findByUser(User user);
	
	

}
