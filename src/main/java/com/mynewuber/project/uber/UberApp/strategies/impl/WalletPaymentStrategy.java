package com.mynewuber.project.uber.UberApp.strategies.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mynewuber.project.uber.UberApp.entities.Driver;
import com.mynewuber.project.uber.UberApp.entities.Payment;
import com.mynewuber.project.uber.UberApp.entities.Rider;
import com.mynewuber.project.uber.UberApp.entities.enums.PaymentStatus;
import com.mynewuber.project.uber.UberApp.entities.enums.TransactionMethod;
import com.mynewuber.project.uber.UberApp.repositories.PaymentRepository;
import com.mynewuber.project.uber.UberApp.services.WalletService;
import com.mynewuber.project.uber.UberApp.strategies.PaymentStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy{

	private final WalletService walletService;
	private final PaymentRepository paymentRepository;
	
	@Override
	@Transactional
	public void processPayment(Payment payment) {
		// TODO Auto-generated method stub
		Driver driver = payment.getRide().getDriver();
		Rider rider = payment.getRide().getRider();
		

        walletService.deductMoneyFromWallet(rider.getUser(),
                payment.getAmount(), null, payment.getRide(), TransactionMethod.RIDE);

        double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(),
                driversCut, null, payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
		paymentRepository.save(payment);
		
		
		
	}

}
