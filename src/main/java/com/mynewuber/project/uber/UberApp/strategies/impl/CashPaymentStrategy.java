package com.mynewuber.project.uber.UberApp.strategies.impl;

import org.springframework.stereotype.Service;

import com.mynewuber.project.uber.UberApp.entities.Driver;
import com.mynewuber.project.uber.UberApp.entities.Payment;
import com.mynewuber.project.uber.UberApp.entities.enums.PaymentStatus;
import com.mynewuber.project.uber.UberApp.entities.enums.TransactionMethod;
import com.mynewuber.project.uber.UberApp.repositories.PaymentRepository;
import com.mynewuber.project.uber.UberApp.services.WalletService;
import com.mynewuber.project.uber.UberApp.strategies.PaymentStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

	private final WalletService walletService;
	private final PaymentRepository paymentRepository;
	@Override
	public void processPayment(Payment payment) {
		// TODO Auto-generated method stub
		
		Driver driver = payment.getRide().getDriver();
		
		//Wallet driverWallet = walletService.findByUser(driver.getUser());
		
		Double platformCommission = payment.getAmount()*PLATFORM_COMMISSION;
		
		walletService.deductMoneyFromWallet(driver.getUser(), platformCommission, null,
                payment.getRide(), TransactionMethod.RIDE);
		
		   payment.setPaymentStatus(PaymentStatus.CONFIRMED);
			paymentRepository.save(payment);
	}

}
