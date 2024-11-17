package com.mynewuber.project.uber.UberApp.services.impl;

import org.springframework.stereotype.Service;

import com.mynewuber.project.uber.UberApp.entities.Payment;
import com.mynewuber.project.uber.UberApp.entities.Ride;
import com.mynewuber.project.uber.UberApp.entities.enums.PaymentStatus;
import com.mynewuber.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.mynewuber.project.uber.UberApp.repositories.PaymentRepository;
import com.mynewuber.project.uber.UberApp.services.PaymentService;
import com.mynewuber.project.uber.UberApp.strategies.PaymentStrategyManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	private final PaymentStrategyManager paymentStrategyManager;
	
	
	
	@Override
	public void processPayment(Ride ride) {
		// TODO Auto-generated method stub
		Payment payment = paymentRepository.findByRide(ride)
							.orElseThrow(() -> new ResourceNotFoundException("Payment not found with id:" 
								+ ride.getId()));
		
		paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
	}

	@Override
	public Payment createNewPayment(Ride ride) {
		// TODO Auto-generated method stub
		Payment payment = Payment.builder()
								.ride(ride)
								.paymentMethod(ride.getPaymentMethod())
								.amount(ride.getFare())
								.paymentStatus(PaymentStatus.PENDING)
								.build();
		return paymentRepository.save(payment);
	}

	@Override
	public void updatePaymentStatus(Payment payment, PaymentStatus status) {
		// TODO Auto-generated method stub
		payment.setPaymentStatus(status);
		paymentRepository.save(payment);
		
	}

}
