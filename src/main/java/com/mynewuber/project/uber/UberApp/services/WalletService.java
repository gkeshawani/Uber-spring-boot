package com.mynewuber.project.uber.UberApp.services;

import com.mynewuber.project.uber.UberApp.entities.Ride;
import com.mynewuber.project.uber.UberApp.entities.User;
import com.mynewuber.project.uber.UberApp.entities.Wallet;
import com.mynewuber.project.uber.UberApp.entities.enums.TransactionMethod;

public interface WalletService {

	 Wallet addMoneyToWallet(User user, Double amount,
             String transactionId, Ride ride,
             TransactionMethod transactionMethod);

Wallet deductMoneyFromWallet(User user, Double amount,
                  String transactionId, Ride ride,
                  TransactionMethod transactionMethod);

	void withdrwMoneyFromWallet();
	Wallet findWalletById(Long walletId);
	
	Wallet createNewWallet(User user);
	Wallet findByUser(User user);
}
