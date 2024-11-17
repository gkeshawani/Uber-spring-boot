package com.mynewuber.project.uber.UberApp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mynewuber.project.uber.UberApp.entities.User;
import com.mynewuber.project.uber.UberApp.entities.Wallet;

@Repository
public interface WalletRepository  extends JpaRepository<Wallet, Long>{

	  Optional<Wallet> findByUser(User user);

}
