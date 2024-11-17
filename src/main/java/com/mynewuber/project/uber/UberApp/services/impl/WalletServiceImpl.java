package com.mynewuber.project.uber.UberApp.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mynewuber.project.uber.UberApp.entities.Ride;
import com.mynewuber.project.uber.UberApp.entities.User;
import com.mynewuber.project.uber.UberApp.entities.Wallet;
import com.mynewuber.project.uber.UberApp.entities.WalletTransaction;
import com.mynewuber.project.uber.UberApp.entities.enums.TransactionMethod;
import com.mynewuber.project.uber.UberApp.entities.enums.TransactionType;
import com.mynewuber.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.mynewuber.project.uber.UberApp.repositories.WalletRepository;
import com.mynewuber.project.uber.UberApp.services.WalletService;
import com.mynewuber.project.uber.UberApp.services.WalletTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
	
	
	private final WalletRepository walletRepository;
	private final WalletTransactionService walletTransactionService;
	
	
	
	@Override
	@Transactional
	 public Wallet addMoneyToWallet(User user, Double amount,
 									String transactionId, Ride ride, 
 									TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()+amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        //walletTransactionService.createNewWalletTransaction(walletTransaction);

        wallet.getTransaction().add(walletTransaction);
        return walletRepository.save(wallet);
    }

	@Override
	@Transactional
	public Wallet deductMoneyFromWallet(User user, Double amount, 
											String transactionId, Ride ride, 
											TransactionMethod transactionMethod) {
		// TODO Auto-generated method stub
		Wallet wallet = findByUser(user);
		 wallet.setBalance(wallet.getBalance()-amount);
		 
		 WalletTransaction walletTransaction = WalletTransaction.builder()
	                .transactionId(transactionId)
	                .ride(ride)
	                .wallet(wallet)
	                .transactionType(TransactionType.DEBIT)
	                .transactionMethod(transactionMethod)
	                .amount(amount)
	                .build();

	        walletTransactionService.createNewWalletTransaction(walletTransaction);

		 
		 
		return walletRepository.save(wallet);
	}
	
	
	@Override
	public void withdrwMoneyFromWallet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Wallet findWalletById(Long walletId) {
		// TODO Auto-generated method stub
		return walletRepository.findById(walletId).orElseThrow(() ->
			new ResourceNotFoundException("Wallet not found with Id:" + walletId));
	}

	@Override
	public Wallet createNewWallet(User user) {
		// TODO Auto-generated method stub
		
		Wallet wallet = new Wallet();
		wallet.setUser(user);
		
		return walletRepository.save(wallet);
	}

	@Override
	public Wallet findByUser(User user) {
		// TODO Auto-generated method stub
		return walletRepository.findByUser(user).orElseThrow(()-> 
				new ResourceNotFoundException("Wallet not found with User id:"+ user.getId() ));
	}

	
	
	

}
