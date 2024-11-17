package com.mynewuber.project.uber.UberApp.services;

import com.mynewuber.project.uber.UberApp.entities.Payment;
import com.mynewuber.project.uber.UberApp.entities.Ride;
import com.mynewuber.project.uber.UberApp.entities.enums.PaymentStatus;

public interface PaymentService {
	
	void processPayment(Ride ride);
	Payment createNewPayment(Ride ride);
	
	void updatePaymentStatus(Payment payment, PaymentStatus status);

}
