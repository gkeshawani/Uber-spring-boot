package com.mynewuber.project.uber.UberApp.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mynewuber.project.uber.UberApp.entities.User;

public interface UserRepository  extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);

}
