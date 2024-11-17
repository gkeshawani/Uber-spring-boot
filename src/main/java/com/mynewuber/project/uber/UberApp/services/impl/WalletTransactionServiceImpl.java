package com.mynewuber.project.uber.UberApp.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mynewuber.project.uber.UberApp.dto.WalletTransactionDto;
import com.mynewuber.project.uber.UberApp.entities.WalletTransaction;
import com.mynewuber.project.uber.UberApp.repositories.WalletTransactionRepository;
import com.mynewuber.project.uber.UberApp.services.WalletTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService{
	
	private final WalletTransactionRepository walletTransactionRepository;
	private final ModelMapper modelMapper;

	@Override
	public void createNewWalletTransaction(WalletTransaction walletTransaction) {
		// TODO Auto-generated method stub
		
		//WalletTransaction walletTransaction = modelMapper.map(walletTransactionDto, WalletTransaction.class);
		walletTransactionRepository.save(walletTransaction);
		
	}
	

}
